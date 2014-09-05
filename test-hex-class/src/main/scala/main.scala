/**
 * main.scala
 */
import Hex._

object Main extends App {
  val ret = new java.io.File("target/scala-2.11/classes/Main.class").bytesAsHex()
  println(ret)
}
