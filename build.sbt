name := "fp-scala"

version := "0.1"

scalaVersion := "2.12.8"

val Http4sVersion = "0.20.15"
val CirceVersion = "0.12.3"
val Specs2Version = "4.8.1"
val LogbackVersion = "1.2.3"

libraryDependencies ++= Seq(
  "org.http4s"      %% "http4s-blaze-server" % Http4sVersion,
  "org.http4s"      %% "http4s-blaze-client" % Http4sVersion,
  "org.http4s"      %% "http4s-circe"        % Http4sVersion,
  "org.http4s"      %% "http4s-dsl"          % Http4sVersion,
  "io.circe"        %% "circe-generic"       % CirceVersion,
  "org.specs2"      %% "specs2-core"         % Specs2Version % "test",
  "ch.qos.logback"  %  "logback-classic"     % LogbackVersion,
  "com.github.t3hnar" %% "scala-bcrypt" % "4.1",
  "com.pauldijou" %% "jwt-circe" % "4.2.0"
)