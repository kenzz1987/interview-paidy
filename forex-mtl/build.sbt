import Dependencies._

name := "forex"
version := "1.0.1"

scalaVersion := "2.13.12"
scalacOptions ++= Seq(
  "-deprecation", // Emit warning and location for usages of deprecated APIs.
  "-encoding",
  "utf-8", // Specify character encoding used by source files.
  "-explaintypes", // Explain type errors in more detail.
  "-feature", // Emit warning and location for usages of features that should be imported explicitly.
  "-language:existentials", // Existential types (besides wildcard types) can be written and inferred
  "-language:experimental.macros", // Allow macro definition (besides implementation and application)
  "-language:higherKinds", // Allow higher-kinded types
  "-language:implicitConversions", // Allow definition of implicit functions called views
  "-unchecked", // Enable additional warnings where generated code depends on assumptions.
  "-Xcheckinit", // Wrap field accessors to throw an exception on uninitialized access.
  "-Xfatal-warnings", // Fail the compilation if there are any warnings.
  "-Xlint:adapted-args", // Warn if an argument list is modified to match the receiver.
  "-Xlint:delayedinit-select", // Selecting member of DelayedInit.
  "-Xlint:inaccessible", // Warn about inaccessible types in method signatures.
  "-Xlint:infer-any", // Warn when a type argument is inferred to be `Any`.
  "-Xlint:missing-interpolator", // A string literal appears to be missing an interpolator id.
  "-Xlint:nullary-unit", // Warn when nullary methods return Unit.
  "-Xlint:option-implicit", // Option.apply used implicit view.
  "-Xlint:package-object-classes", // Class or object defined in package object.
  "-Xlint:poly-implicit-overload", // Parameterized overloaded implicit methods are not visible as view bounds.
  "-Xlint:private-shadow", // A private field (or class parameter) shadows a superclass field.
  "-Xlint:stars-align", // Pattern sequence wildcard must align with sequence component.
  "-Xlint:type-parameter-shadow", // A local type parameter shadows a type already in scope.
  "-Xlog-reflective-calls", // Print a message when a reflective method call is generated
  "-Ywarn-dead-code", // Warn when dead code is identified.
  "-Ywarn-value-discard", // Warn when non-Unit expression results are unused.
  "-Xlint:constant", // Evaluation of a constant arithmetic expression results in an error.
  "-Ywarn-extra-implicit", // Warn when more than one implicit parameter section is defined.
  "-Ywarn-macros:after", // Fixes false warnings associated with generic derivations
  "-Ywarn-unused:implicits", // Warn if an implicit parameter is unused.
  "-Ywarn-unused:imports", // Warn if an import selector is not referenced.
  "-Ywarn-numeric-widen", // Warn when numerics are widened.
  "-Ywarn-unused:locals", // Warn if a local definition is unused.
  "-Ywarn-unused:params", // Warn if a value parameter is unused.
  "-Ywarn-unused:patvars", // Warn if a variable bound in a pattern is unused.
  "-Ywarn-unused:privates", // Warn if a private member is unused.
  "-Ycache-plugin-class-loader:last-modified", // Enables caching of classloaders for compiler plugins
  "-Ycache-macro-class-loader:last-modified" // and macro definitions. This can lead to performance improvements.
)

resolvers +=
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

libraryDependencies ++= Seq(
  compilerPlugin(Libraries.kindProjector),
  Libraries.cats,
  Libraries.catsEffect,
  Libraries.fs2,
  Libraries.http4sDsl,
  Libraries.http4sServer,
  Libraries.http4sClient,
  Libraries.http4sCirce,
  Libraries.circeCore,
  Libraries.circeGeneric,
  Libraries.circeGenericExt,
  Libraries.circeParser,
  Libraries.pureConfig,
  Libraries.logback,
  Libraries.scalaTest      % Test,
  Libraries.scalaCheck     % Test,
  Libraries.catsScalaCheck % Test
)

enablePlugins(JavaAppPackaging, DockerPlugin)

dockerBaseImage := "openjdk:17-slim"
dockerExposedPorts := Seq(8081)
maintainer := "Kendy Yomarno <kenzz1987@gmail.com>"
