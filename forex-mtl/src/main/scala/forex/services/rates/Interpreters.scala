package forex.services.rates

import cats.Applicative
import cats.effect.ConcurrentEffect
import interpreters._
import java.time.Duration
import scala.concurrent.ExecutionContext

object Interpreters {
  def dummy[F[_]: Applicative]: Algebra[F] = new OneFrameDummy[F]()
  def oneFrame[F[_]: ConcurrentEffect](ec: ExecutionContext, cacheDuration: Duration, apiUri: String, apiToken: String): Algebra[F] =
    new OneFrameInterpreter[F](ec, cacheDuration, apiUri, apiToken)
}
