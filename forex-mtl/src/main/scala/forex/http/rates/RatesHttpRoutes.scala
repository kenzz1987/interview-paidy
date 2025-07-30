package forex.http
package rates

import cats.effect.Sync
import cats.syntax.flatMap._
import forex.programs.RatesProgram
import forex.programs.rates.{ Protocol => RatesProgramProtocol }
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.server.Router
import forex.domain.Currency
import forex.domain.Currency.Unknown

class RatesHttpRoutes[F[_]: Sync](rates: RatesProgram[F]) extends Http4sDsl[F] {

  import Converters._, QueryParams._, Protocol._

  private[http] val prefixPath = "/rates"

  private val httpRoutes: HttpRoutes[F] = HttpRoutes.of[F] {
    case GET -> Root :? FromQueryParam(from) +& ToQueryParam(to) =>
      (from, to) match {
        case (Unknown, _) | (_, Unknown) | (Unknown, Unknown) =>
          BadRequest(s"Supported currencies are : ${Currency.allSupportedString}")
        case _ =>
          rates.get(RatesProgramProtocol.GetRatesRequest(from, to)).flatMap(Sync[F].fromEither).flatMap { rate =>
            Ok(rate.asGetApiResponse)
          }
      }
    case req =>
      NotFound(s"Invalid request to $prefixPath with method: ${req.method.name}")
  }

  val routes: HttpRoutes[F] = Router(
    prefixPath -> httpRoutes
  )

}
