/**
 * main.scala
 */
import Hex._
import Files._

object Main extends App {
  val ret = new java.io.File("target/scala-2.11/classes/Main.class").bytes().toHex()
  println(ret)
  
  println("*" * 100)

  println("hello".getBytes().toHex)
}
