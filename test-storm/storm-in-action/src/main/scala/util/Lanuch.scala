package util

trait Lanuch extends Function2[String, Boolean, Unit] {

  def apply(name: String, remote: Boolean): Unit

}
