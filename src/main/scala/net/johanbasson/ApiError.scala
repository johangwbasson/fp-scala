package net.johanbasson

sealed trait ApiError

case class UserNotFound() extends ApiError
case class PasswordsDoesNotMatch() extends ApiError