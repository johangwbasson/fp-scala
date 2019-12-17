package net.johanbasson.security

import java.time.Instant

import cats.effect.IO
import net.johanbasson.{ApiError, PasswordsDoesNotMatch, UserNotFound}
import net.johanbasson.user.{User, UserRepository}
import com.github.t3hnar.bcrypt._
import pdi.jwt.{JwtAlgorithm, JwtCirce, JwtClaim}

trait SecurityService[F[_]] {
  def authenticate(email: String, password: String): F[Either[ApiError, Token]]
}

class BasicSecurityService(userRepository: UserRepository) extends SecurityService[IO] {

  val key = "secretKey"

  override def authenticate(email: String, password: String): IO[Either[ApiError, Token]] = {
    IO {
      for {
        optUsr <- userRepository.findByEmail(email).unsafeRunSync()
        user <- checkUserExists(optUsr)
        _ <- checkPassword(user, password)
        token <- generateToken(user)
      } yield token
    }
  }

  private def checkUserExists(opt: Option[User]): Either[ApiError, User] = {
    opt match {
      case None => Left(UserNotFound())
      case Some(u) => Right(u)
    }
  }

  private def checkPassword(user: User, plain: String): Either[ApiError, User] =
    if (plain.isBcrypted(user.password)) {
      Right(user)
    } else {
      Left(PasswordsDoesNotMatch())
    }

  private def generateToken(user: User): Either[ApiError, Token] = {
    val claim = JwtClaim(expiration = Some(Instant.now.plusSeconds(157784760).getEpochSecond),
      issuedAt = Some(Instant.now.getEpochSecond),
      subject = Some(user.id.toString))
    Right(Token(JwtCirce.encode(claim, key, JwtAlgorithm.HS256)))
  }
}