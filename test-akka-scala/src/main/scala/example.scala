package example

import java.util.Date

import scala.concurrent._
import ExecutionContext.Implicits.global
import duration._

import scatang._

import akka.actor.{ ActorSystem, Props, Actor, ActorRef }

sealed trait Cmd
case object StopActor extends Cmd
case object StopSystem extends Cmd
case class Message(msg: String) extends Cmd

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
      case Message(msg) => {
        count += 1
        if (count > 9990)
          println(msg)
      }
    }
  }

  def main(args: Array[String]): Unit = {
    val (_, (actorSystem: ActorSystem, actor: ActorRef)) = time {
      ActorSystem("test") |> { it =>
        (it, it.actorOf(Props[MyActor], "myactor"))
      }
    }

    print("enter for yes:")

    val f = Future { scala.io.StdIn.readLine }
    try {
      Await.result(f, 3.seconds)
    } catch {
      case e: Exception => {
        println(e.getMessage())
        println("等不了， 直接执行!")
      }
    }

    time {
      for (i <- (0 until 10000)) {
        actor ! Message(new Date().toString())
      }

      actor ! StopSystem
      actorSystem.awaitTermination(10.seconds)
    }

    println("exit")
  }
}
