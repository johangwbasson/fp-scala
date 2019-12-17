package net.johanbasson

import java.util.UUID

package object user {

  case class User(id: UUID, email: String, password: String)

}
