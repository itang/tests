import java.util.Date

import com.google.common.eventbus.{EventBus, Subscribe}
//import scala.collection.JavaConverters._
import util._

import scala.jdk.CollectionConverters._

object Main {

  sealed abstract class Message

  case class Created() extends Message

  case class Running(time: Date) extends Message

  def main(args: Array[String]): Unit = time {
    println(s"args: ${args.mkString(" ")}")
    System.getenv.asScala.filter(_._1.startsWith("ENV")) foreach println

    "Hello, World"
      .$$(println)
      .|>(_ + ", " + new Date().format("yyyy-MM-dd"))
      .|>(println)
    println(Thread.currentThread())
    val ebus = new EventBus("default")
    ebus.register(new Consumer1)
    ebus.post(Created())
    ebus.post(Running(new Date()))

    System.in.read()
  }

  class Consumer1 {
    @Subscribe
    def handle(created: Created) = {
      println(s"${Thread.currentThread()} created")
    }

    @Subscribe
    def handle2(running: Running) = {
      println(s"${Thread.currentThread()} ${running}")
    }
  }

}
