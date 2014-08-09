package test_undertow

import scala.language.implicitConversions

import io.undertow.server.HttpHandler
import io.undertow.server.HttpServerExchange

trait Util {
  implicit def f2HttpHandler(f: HttpServerExchange => Unit): HttpHandler = new HttpHandler() {
    override def handleRequest(exchange: HttpServerExchange): Unit = f(exchange)
  }
}