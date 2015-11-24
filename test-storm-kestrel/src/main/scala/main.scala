import backtype.storm.spout.KestrelThriftClient
import scala.io.StdIn

object Main {

  def main(args: Array[String]): Unit = {

    val client = new KestrelThriftClient("localhost", 2229)

    var loop = true
    while (loop) {
      StdIn.readLine() match {
        case null => println("readline => null")
        case msg if msg.nonEmpty => {
          println(s"put $msg to access quene...")
          client.put("access", msg, 0)
        }
        case _ => loop = false
      }
    }

    println("Exit!")
  }

}
