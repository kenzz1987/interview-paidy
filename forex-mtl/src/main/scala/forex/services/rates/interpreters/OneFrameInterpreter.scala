package forex.services.rates.interpreters

import forex.services.rates.Algebra
import cats.effect.ConcurrentEffect
import cats.syntax.either._
import cats.syntax.functor._
import cats.syntax.flatMap._
import forex.domain.{ Price, Rate, Timestamp}
import forex.services.rates.errors._
import org.http4s.Uri
import org.http4s.blaze.client.BlazeClientBuilder
import org.http4s.{Request, Method, Headers, Header}
import org.typelevel.ci._
import scala.concurrent.ExecutionContext
import io.circe.parser.decode
import java.time.{Instant, Duration, OffsetDateTime}
import scala.collection.immutable.Map
import cats.effect.concurrent.Ref
import forex.http.rates.Protocol.RateResponse
import forex.domain.Currency

class OneFrameInterpreter[F[_]: ConcurrentEffect](ec: ExecutionContext, 
                                                  cacheDuration: Duration,
                                                  apiUri: String,
                                                  apiToken: String) extends Algebra[F] {
  private val cacheRef: Ref[F, Map[Rate.Pair, (Rate, Instant)]] =
    Ref.unsafe(Map.empty[Rate.Pair, (Rate, Instant)])
  override def get(pair: Rate.Pair): F[Error Either Rate] = {
    if (pair.from == pair.to) {
      getSamePairRate(pair)
    } else if (pair.from == Currency.USD || pair.to == Currency.USD) {
      getRateFromCacheOrApi(pair)
    } else {
      getCrossRateFromCacheOrApi(pair)
    }
  }

  private def getSamePairRate(pair: Rate.Pair): F[Error Either Rate] = {
    val rate = Rate(pair, Price(BigDecimal(1)), Timestamp.now)
    ConcurrentEffect[F].pure(rate.asRight[Error])
  }

  private def getCrossRateFromCacheOrApi(pair: Rate.Pair): F[Error Either Rate] = {
    val pair1 = Rate.Pair(pair.from, Currency.USD)
    val pair2 = Rate.Pair(Currency.USD, pair.to)
    for {
      rate1 <- getRateFromCacheOrApi(pair1)
      rate2 <- getRateFromCacheOrApi(pair2)
    } yield {
      (rate1, rate2) match {
        case (Right(r1), Right(r2)) =>
          val price = Price(r1.price.value * r2.price.value)
          val timestamp = if (r1.timestamp.value.isAfter(r2.timestamp.value)) r1.timestamp else r2.timestamp
          Rate(pair, price, timestamp).asRight[Error]
        case (Left(error), _) => error.asLeft[Rate]
        case (_, Left(error)) => error.asLeft[Rate]
      }
    }
  }

  private def getRateFromCacheOrApi(pair: Rate.Pair): F[Error Either Rate] = {
    ConcurrentEffect[F].delay(Instant.now()).flatMap { now =>
        cacheRef.get.flatMap { cache =>
            cache.get(pair) match {
              case Some((rate, timestamp)) if Duration.between(timestamp, now).compareTo(cacheDuration) < 0 =>
                ConcurrentEffect[F].pure(rate.asRight[Error])
              case _ =>
                fetchRateFromOneFrame(pair).flatMap {
                  case Right(rate) => updateCache(pair, rate).as(rate.asRight[Error])
                  case Left(error) => ConcurrentEffect[F].pure(error.asLeft[Rate])
                }
            }
        }
    }
  }

  private def updateCache(pair: Rate.Pair, rate: Rate): F[Unit] = {
    cacheRef.update(_ + (pair -> (rate, Instant.now())))
  }

  private def fetchRateFromOneFrame(pair: Rate.Pair): F[Error Either Rate] = {
    val request = getRequest
    callOneFrameApi(request, pair)
  }

  private def getRequest: Request[F] = {
    val pairStr = Currency.proxyPairsQueryString
    val uri = Uri.unsafeFromString(s"$apiUri/rates?pair=$pairStr")
    val tokenHeader = Header.Raw(ci"token", apiToken)
    Request[F](
      method = Method.GET,
      uri = uri,
      headers = Headers.apply(tokenHeader)
    )
  }

  private def callOneFrameApi(request: Request[F], pair: Rate.Pair): F[Error Either Rate] = {
    BlazeClientBuilder[F](ec).resource.use { client =>
      client.expect[String](request).map { response =>
        parseRate(pair, response).asRight[Error]
      }
    }
  }

  private def parseRate(pair: Rate.Pair, response: String): Rate = {
    decode[List[RateResponse]](response) match {
      case Right(rateList) if rateList.nonEmpty =>
        val r = rateList.head
        val price = Price(r.price)
        val timestamp = Timestamp(OffsetDateTime.parse(r.time_stamp))
        Rate(pair, price, timestamp)
      case _ =>
        Rate(pair, Price(BigDecimal(0)), Timestamp.now) 
    }
  }
}
