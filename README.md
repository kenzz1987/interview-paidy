# Candidate : Kendy Yomarno

## Notes
- This fork is created on 23 Jul 2025 around 22:00
- Due to my work laptop's monitoring, I'd like to avoid working using that and I need to setup everything in my PC (VSCode + extensions, docker, etc)
- Preparations completed on 26 Jul 2025 (able to run both OneFrame service using docker desktop and forex-mtl using VS Code extension Scala Metals)
- inbetween preparations, I also familiarize myself with Functional Programming since I don't really have any experience with FP before
- some ideas before development started :
  - limitation on age of the rates < 5 minutes with 1,000 requests a day (fortunately the oneframe service allows multiple pair request at one time, if I assume it right) means I could only make 41.67 request an hour or one request every 86.4 seconds or every ~1.5 minutes to be safe
  - I would cache every rates every 2 minutes given that condition (considering gap between the age of expiring cache and the time needed to fetch new rates to overwrite the cache -- need to test this further and adjust the frequency of 2 minutes)
  - if there is any requests inbetween cache refreshes, take the rates directly from the cache
  - depends on the response time of oneframe service, I would like to increase the currency pool beyond what's provided but if the oneframe service can't handle too many pairs at one request, I will reduce the pool step by step
  - finally, implement rate limiter for 10,000 request (optional only when the successful return rates from oneframe service is lower than 80%)
  - when supplied with same currency pair (e.g USD to USD), return 1 immediately
  - cache in combination, not permutation (there will be only one record in the cache for USD x JPY e.g USDJPY), if the request is JPY to USD, just reverse the rate -- 1 / USDJPY_rate

## Development
- docker run OneFrame API & run forex-mtl using Scala Metals extension in VS Code
- test connectivity to OneFrame API from forex-mtl
- setup config to host the application
- add dependency for http4s-blaze-client
- redirect API calls to OneFrame API first
- add caching for single pair (redirect API calls to OneFrame API when cache miss), add config for cache duration of 2 minutes
- apply rate limiter to ensure maximum of 10,000 calls a day
- extends currency pool based on supported currencies in OneFrame API
- test connectivity to OneFrame API using combinations of all supported currencies (failed, querystring is too long -- nearly 10k pairs in total)
- workaround using proxy currency (USD) to handle too many combinations
- adjust cache hit using proxy currency when needed
- add validations to ensure only accepting GET requests with supported currencies
- move decoder to Protocol.scala since it seems more suitable there
- move API token and URL to config
