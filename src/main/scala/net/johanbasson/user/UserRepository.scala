package net.johanbasson.user

import java.util.UUID

import cats.effect.IO
import com.github.t3hnar.bcrypt._
import net.johanbasson.ApiError

trait UserRepository {
  def findByEmail(email: String): IO[Either[ApiError, Option[User]]]
}

class BasicUserRepository extends UserRepository {
  override def findByEmail(email: String): IO[Either[ApiError, Option[User]]] = {
    IO {
      if (email == "admin") {
        Right(Some(User(UUID.randomUUID(), "admin", "admin".bcrypt)))
      } else {
        Right(None)
      }
    }
  }
}