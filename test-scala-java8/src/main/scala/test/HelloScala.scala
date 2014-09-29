package test

import language.postfixOps

import scatang._

import scala.concurrent._
import ExecutionContext.Implicits.global
import duration._

object HelloScala extends App {
  new Thread((() ⇒ {
    Thread.sleep(200.milliseconds.toMillis)
    println("Hello,World (From Scala)")
  }): Runnable).start()

  val future = Future {
    List("Hello", "World") map (_.toUpperCase()) tap (_ foreach println)
  }

  Await.result(future, 1 seconds)

  private def println(msg: Any): Unit = scala.Console.println(Thread.currentThread() + ": " + msg)
}
