package forex.config

import scala.concurrent.duration.FiniteDuration

case class ApplicationConfig(
    http: HttpConfig,
    rates: RatesConfig,
    limiter: LimiterConfig
)

case class HttpConfig(
    host: String,
    port: Int,
    timeout: FiniteDuration
)

case class RatesConfig(
    cache: FiniteDuration,
    uri: String,
    token: String
)

case class LimiterConfig(
    rate: Int,
    window: FiniteDuration
)
