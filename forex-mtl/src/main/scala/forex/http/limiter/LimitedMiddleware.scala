package forex.http.limiter

import cats.effect.{Sync, Clock}
import cats.effect.concurrent.Ref
import cats.syntax.all._
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import scala.concurrent.duration._

class LimitedMiddleware[F[_]: Sync: Clock](
  rateLimit: Int = 10000,
  window: FiniteDuration = 1.day
) extends Http4sDsl[F] {

  private val requestCountRef = Ref.unsafe[F, (Int, Long)]((0, 0L))

  def apply(routes: HttpRoutes[F]): HttpRoutes[F] = HttpRoutes.of[F] { req =>
    Clock[F].realTime(MILLISECONDS).flatMap { now =>
      requestCountRef.get.flatMap {
        case (count, windowStart) if now - windowStart < window.toMillis =>
          if (count < rateLimit) {
            requestCountRef.update(c => (c._1 + 1, c._2)) *> routes(req).getOrElseF(NotFound())
          } else {
            TooManyRequests("Rate limit exceeded")
          }
        case _ =>
          requestCountRef.set((1, now)) *> routes(req).getOrElseF(NotFound())
      }
    }
  }
}