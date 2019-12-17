package net.johanbasson

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import net.johanbasson.security.BasicSecurityService
import net.johanbasson.user.BasicUserRepository
import org.http4s.implicits._
import org.http4s.server.Router
import org.http4s.server.blaze.BlazeServerBuilder

object Main extends IOApp {
  private val routes = new Routes(new BasicSecurityService(new BasicUserRepository()))

  private val httpRoutes = Router[IO](
    "/" -> routes.routes
  ).orNotFound

  override def run(args: List[String]): IO[ExitCode] = {
    BlazeServerBuilder[IO]
      .bindHttp(7125, "0.0.0.0")
      .withHttpApp(httpRoutes)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
  }

}
