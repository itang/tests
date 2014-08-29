/**
 * main.scala
 */
import java.util.Date

import scala.language.postfixOps
import scala.collection.mutable.{ HashMap => MutableHashMap }
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import akka.pattern.ask
import akka.util.Timeout

import scatang._

sealed trait Cmd

case object StopActor extends Cmd
case object StopSystem extends Cmd
case object GetThreads extends Cmd
case class Message(msg: String) extends Cmd

class MyActor extends Actor {
  var count = 0
  var map = MutableHashMap[Long, Long]()

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

object Main {

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
