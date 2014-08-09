package test_undertow

import io.undertow.Undertow
import io.undertow.server.HttpHandler
import io.undertow.server.HttpServerExchange
import io.undertow.util.Headers

import scala.collection.JavaConversions._
import scatang.string._

object Server extends App with Util {

  val server: Undertow = Undertow.builder().addHttpListener(8080, "localhost").setHandler { exchange: HttpServerExchange =>
    for (header <- exchange.getRequestHeaders()) {
      println(header.getHeaderName().toString().padRight(20) + ":" + header.mkString(","))
    }

    exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain")
    exchange.getResponseSender().send("Hello, world!")
  }.build()

  server.start()
}

