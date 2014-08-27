package example

import java.util.Date

import scala.concurrent._
import ExecutionContext.Implicits.global
import duration._

import scatang._

import akka.actor.{ ActorSystem, Props, Actor, ActorRef }

trait Cmd
sealed case class StopActor() extends Cmd
sealed case class StopSystem() extends Cmd
sealed case class Content(msg: String) extends Cmd

object Example {

  class MyActor extends Actor {
    var count = 0;
    override def receive = {
      case StopSystem => {
        println("receive exit system message")
        context.system.shutdown()
      }
      case StopActor => {
        println("receive stop the actor message")
        context.stop(self)
      }
      case Content(msg) => {
        count += 1
        if (count > 9990)
          println(msg)
      }
    }
  }

  def main(args: Array[String]): Unit = {
    var actorSystem: ActorSystem = null
    var actor: ActorRef = null
    time {
      actorSystem = ActorSystem("test")
      actor = actorSystem.actorOf(Props[MyActor], "myactor")
    }

    print("enter for yes:")

    val f = Future { scala.io.StdIn.readLine }
    try {
      Await.result(f, 3.seconds)
    } catch {
      case e: Exception => println(e.getMessage())
    }

    println("等不了， 直接执行!")

    time {
      for (i <- (0 until 10000)) {
        actor ! Content(new Date().toString())
      }

      actor ! StopSystem
      actorSystem.awaitTermination(10.seconds)
    }

    println("exit")
  }
}
