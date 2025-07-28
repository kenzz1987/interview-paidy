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
import io.circe.{Decoder, HCursor}
import java.time.{Instant, Duration}
import scala.collection.immutable.Map
import cats.effect.concurrent.Ref

case class RateResponse(price: BigDecimal)

object RateResponse {
  implicit val rateResponseDecoder: Decoder[RateResponse] = (c: HCursor) => for {
    price <- c.downField("price").as[BigDecimal]
  } yield RateResponse(price)
}

class OneFrameInterpreter[F[_]: ConcurrentEffect](ec: ExecutionContext, cacheDuration: Duration) extends Algebra[F] {
  private val cacheRef: Ref[F, Map[Rate.Pair, (Rate, Instant)]] =
    Ref.unsafe(Map.empty[Rate.Pair, (Rate, Instant)])
  override def get(pair: Rate.Pair): F[Error Either Rate] = {
    if (pair.from == pair.to) {
      returnSamePairRate(pair)
    } else {
      getFromCacheOrApi(pair)
    }
  }

  private def returnSamePairRate(pair: Rate.Pair): F[Error Either Rate] = {
    val rate = Rate(pair, Price(BigDecimal(1)), Timestamp.now)
    ConcurrentEffect[F].pure(rate.asRight[Error])
  }

  private def getFromCacheOrApi(pair: Rate.Pair): F[Error Either Rate] = {
    ConcurrentEffect[F].delay(Instant.now()).flatMap { now =>
        cacheRef.get.flatMap { cache =>
            cache.get(pair) match {
              case Some((rate, timestamp)) if Duration.between(timestamp, now).compareTo(cacheDuration) < 0 =>
                  val updatedRate = rate.copy(timestamp = Timestamp.now)
                  ConcurrentEffect[F].pure(updatedRate.asRight[Error])
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
    val request = getRequest(pair)
    callOneFrameApi(request, pair)
  }

  private def getRequest(pair: Rate.Pair): Request[F] = {
    val pairStr = s"${pair.from}${pair.to}"
    val uri = Uri.unsafeFromString(s"http://localhost:8080/rates?pair=$pairStr")
    val tokenHeader = Header.Raw(ci"token", "10dc303535874aeccc86a8251e6992f5")
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
        val timestamp = Timestamp.now
        Rate(pair, price, timestamp)
      case _ =>
        Rate(pair, Price(BigDecimal(0)), Timestamp.now) 
    }
  }
}
