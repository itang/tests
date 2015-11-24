import backtype.storm.spout.KestrelThriftClient
import scala.io.StdIn

object Main {

  def main(args: Array[String]): Unit = {

    val client = new KestrelThriftClient("localhost", 2229)

    var loop = true
    while (loop) {
      val msg = StdIn.readLine()

      msg match {
        case null => println("readline => null")
        case e if e.nonEmpty => {
          println(s"put $msg to access quene...")
          client.put("access", msg, 0)
        }
        case _ => loop = false
      }

    }
    println("Exit!")
  }
}

