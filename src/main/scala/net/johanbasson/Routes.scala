package net.johanbasson

import cats.effect.IO
import io.circe.generic.auto._
import net.johanbasson.security.SecurityService
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.Http4sDsl

case class AuthenticateRequest(email: String, password: String)

class Routes(securityService: SecurityService[IO]) extends Http4sDsl[IO] {
  val routes = HttpRoutes.of[IO] {
    case req @ POST -> Root / "authenticate" =>
      for {
        authReq <- req.as[AuthenticateRequest]
        res <- securityService.authenticate(authReq.email, authReq.password)
        resp <- res.fold(
          { err => BadRequest(err) },
          { token => Ok(token)}
        )
      } yield resp
  }
}


