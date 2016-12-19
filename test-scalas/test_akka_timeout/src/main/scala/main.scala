import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props, ReceiveTimeout, Terminated}

import scala.concurrent.duration.{Duration, _}

object Constant {
  val S = 1000
  val E = 1000
}

import Constant._

class Terminator(d: Duration, ref: ActorRef) extends Actor with ActorLogging {
  context.setReceiveTimeout(d)

  def receive = {
    case ReceiveTimeout =>
      //println(Thread.currentThread() + ": tick " + d.toSeconds)
      context.setReceiveTimeout(Duration.Inf);
      ref ! Thread.currentThread() + ": tick " + d.toMicros
      context.stop(self)

    case Terminated(_) =>
      log.info("{} has terminated, shutting down system")
      context.system.terminate()
  }
}

class Echo extends Actor with ActorLogging {
  var i = 0

  override def receive = {
    case message =>
      i += 1
      println(s"echo_${i}: ${message} ")
      if (i == S * E) {
        log.info("shutting down system")
        context.system.terminate()
      }
  }
}


object Main {

  def main(args: Array[String]): Unit = {
    val system = ActorSystem("Hello")

    val echo = system.actorOf(Props(classOf[Echo]))
    for (i <- 1 to S;
         j <- 1 to E) {
      val _t = system.actorOf(Props(classOf[Terminator], (i + j).microsecond, echo))
    }
  }
}
