package example

import scala.language.postfixOps

import java.util.Date

import scala.concurrent._
import ExecutionContext.Implicits.global
import duration._

import scatang._

import akka.actor.{ ActorSystem, Props, Actor, ActorRef }
import akka.pattern.ask
import akka.util.Timeout

sealed trait Cmd
case object StopActor extends Cmd
case object StopSystem extends Cmd
case class Message(msg: String) extends Cmd
case object GetThreads extends Cmd

object Example {

  class MyActor extends Actor {
    var count = 0;
    var map = scala.collection.mutable.HashMap[Long, Long]()
    override def receive = {
      case StopSystem => {
        println(s"receive exit system message, message total:$count")
        context.system.shutdown()
      }
      case StopActor => {
        println("receive stop the actor message")
        context.stop(self)
      }
      case GetThreads => this.sender ! map
      case Message(msg) => {
        val tid = Thread.currentThread().getId()
        map += (tid -> (map.getOrElse(tid, 0L) + 1L))
        count += 1
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
      Await.result(f, 1.seconds)
    } catch {
      case e: Exception => {
        println(e.getMessage())
        println("等不了， 直接执行!")
      }
    }

    time {
      for (i <- (0 until 10000000)) {
        actor ! Message(new Date().toString())
      }

      implicit val timeout = Timeout(5 seconds)
      val future = actor ? GetThreads // enabled by the “ask” import
      val result = Await.result(future, 2.seconds).asInstanceOf[scala.collection.mutable.HashMap[Long, Long]]
      println(s"use threads: ${result.size}")
      for ((k, v) <- result) {
        println(s"thread-$k, messages: $v")
      }

      actor ! StopSystem
      actorSystem.awaitTermination(10.seconds)
    }

    println("exit")
  }
}
