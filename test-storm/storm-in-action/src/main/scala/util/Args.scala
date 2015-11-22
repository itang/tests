package util

object Args {
  def unapply(args: Array[String]): Option[(String, Boolean)] = {
    args.toList match {
      case name :: Nil         => Some((name, false))
      case name :: rs :: _rest => Some((name, rs.toBoolean))
      case _                   => None
    }
  }
}

case class Args(name: String, remote: Boolean = false)
