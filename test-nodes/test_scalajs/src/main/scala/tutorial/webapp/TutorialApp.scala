package tutorial.webapp

import io.scalajs.nodejs.http._
import io.scalajs.nodejs.process

import scalajs.js
import util.Pipe

import scala.util.Try

object TutorialApp {
  val DefaultPort = 3000

  def main(_args: Array[String]): Unit = {
    println(process.argv.toList)

    Http.createServer {
      (_: ClientRequest, response: ServerResponse) =>
        response
          .$$(_.writeHead(200, js.Dictionary("Content-Type" -> "text/plain")))
          .$$(_.write("Hello World"))
          .|>(_.end())
    }.listen(port)
  }

  private def port: Int =
    (process.argv.toList match {
      case List(_node, _script, port) => Try(port.toInt).toOption
      case List(_node, port) => Try(port.toInt).toOption
      case _ => None
    })
      .$$(it => println(s"port from args: $it"))
      .getOrElse(DefaultPort.$$(it => println(s"port use default: $it")))

}
