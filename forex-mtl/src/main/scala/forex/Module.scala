package forex

import cats.effect.{ Timer, ConcurrentEffect }
import forex.config.ApplicationConfig
import forex.http.rates.RatesHttpRoutes
import forex.services._
import forex.services.rates.Algebra
import forex.programs._
import org.http4s._
import org.http4s.implicits._
import org.http4s.server.middleware.{ AutoSlash, Timeout }
import scala.concurrent.ExecutionContext
import forex.http.limiter.LimitedMiddleware

class Module[F[_]: Timer: ConcurrentEffect](config: ApplicationConfig) {
  private implicit val ec: ExecutionContext = ExecutionContext.global

  private val cacheDuration = java.time.Duration.ofMillis(config.rates.cache.toMillis)
  private val apiUri = config.rates.uri
  private val apiToken = config.rates.token

  private val ratesService: Algebra[F] = RatesServices.oneFrame[F](ec, cacheDuration, apiUri, apiToken)

  private val ratesProgram: RatesProgram[F] = RatesProgram[F](ratesService)

  private val ratesHttpRoutes: HttpRoutes[F] = new RatesHttpRoutes[F](ratesProgram).routes

  private val limitedMiddleware = new LimitedMiddleware[F](config.limiter.rate, config.limiter.window)

  type PartialMiddleware = HttpRoutes[F] => HttpRoutes[F]
  type TotalMiddleware   = HttpApp[F] => HttpApp[F]

  private val routesMiddleware: PartialMiddleware = {
    { http: HttpRoutes[F] =>
      limitedMiddleware(AutoSlash(http))
    }
  }

  private val appMiddleware: TotalMiddleware = { http: HttpApp[F] =>
    Timeout(config.http.timeout)(http)
  }

  private val http: HttpRoutes[F] = ratesHttpRoutes

  val httpApp: HttpApp[F] = appMiddleware(routesMiddleware(http).orNotFound)

}
