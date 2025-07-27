package forex.services.rates.interpreters

import forex.services.rates.Algebra
import cats.effect.ConcurrentEffect
import cats.syntax.either._
import cats.syntax.functor._
import forex.domain.{ Price, Rate, Timestamp}
import forex.services.rates.errors._
import org.http4s.Uri
import org.http4s.blaze.client.BlazeClientBuilder
import org.http4s.{Request, Method, Headers, Header}
import org.typelevel.ci._
import scala.concurrent.ExecutionContext
import io.circe.parser.decode
import io.circe.{Decoder, HCursor}

case class RateResponse(price: BigDecimal)

object RateResponse {
  implicit val rateResponseDecoder: Decoder[RateResponse] = (c: HCursor) => for {
    price     <- c.downField("price").as[BigDecimal]
  } yield RateResponse(price)
}

class OneFrameInterpreter[F[_]: ConcurrentEffect](ec: ExecutionContext) extends Algebra[F] {
  override def get(pair: Rate.Pair): F[Error Either Rate] = {
    if (pair.from == pair.to) {
      ConcurrentEffect[F].pure(Rate(pair, Price(BigDecimal(1)), Timestamp.now).asRight[Error])
    } else {
      fetchRateFromOneFrame(pair)
    }
  }

  private def fetchRateFromOneFrame(pair: Rate.Pair): F[Error Either Rate] = {
    val pairStr = s"${pair.from}${pair.to}"
    val uri = Uri.unsafeFromString(s"http://localhost:8080/rates?pair=$pairStr")
    val tokenHeader = Header.Raw(ci"token", "10dc303535874aeccc86a8251e6992f5")
    val request = Request[F](
      method = Method.GET,
      uri = uri,
      headers = Headers.apply(tokenHeader)
    )
    
    BlazeClientBuilder[F](ec).resource.use { client =>
      client.expect[String](request).map { response =>
        println(s"Received response: $response")
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
