import io.undertow.Undertow
import io.undertow.server.HttpHandler
import io.undertow.server.HttpServerExchange
import io.undertow.util.Headers
import scala.collection.JavaConversions._

object Server extends App {
  implicit class StringWrapper(s: String) {
    def padRight(length: Int, p: String = " "): String = pad(length, p, true)

    def padLeft(length: Int, p: String = " "): String = pad(length, p, false)

    def pad(length: Int, p: String = " ", right_? : Boolean = true): String =
      s.length() match {
        case len if len >= length => s
        case len => {
          val pl = length - len
          if (right_?) s + p * pl else p * pl + s
        }
      }
  }

  val server: Undertow = Undertow.builder().addHttpListener(8080, "localhost").setHandler(new HttpHandler() {
    override def handleRequest(exchange: HttpServerExchange): Unit = {

      for (header <- exchange.getRequestHeaders()) {
        println(header.getHeaderName().toString().padRight(20) + ":" + header.mkString(","))
      }

      exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain")
      exchange.getResponseSender().send("Hello, world!")
    }
  }).build()

  server.start()
}

