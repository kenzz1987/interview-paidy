package forex.services.rates

import cats.Applicative
import interpreters._
import scala.concurrent.ExecutionContext

object Interpreters {
  def dummy[F[_]: Applicative]: Algebra[F] = new OneFrameDummy[F]()
  import cats.effect.ConcurrentEffect

  def oneFrame[F[_]: ConcurrentEffect](ec: ExecutionContext): Algebra[F] = new OneFrameInterpreter[F](ec)
}
