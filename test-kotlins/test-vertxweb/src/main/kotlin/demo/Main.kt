package demo

import io.vertx.core.Vertx
import io.vertx.core.http.HttpServer
import io.vertx.core.http.HttpServerResponse
import io.vertx.ext.web.Route
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext

fun Router.get(path: String, handler: (RoutingContext) -> Unit): Route {
    return this.get(path).handler { ctx -> handler(ctx) }
}

fun Router.all(path: String, handler: (RoutingContext) -> Unit): Route {
    return route(path).handler { ctx -> handler(ctx) }
}

fun Router.all(handler: (RoutingContext) -> Unit): Route {
    return route().handler { ctx -> handler(ctx) }
}

fun Vertx.router(): Router {
    return Router.router(this)
}

fun main(args: Array<String>) {
    //val server: HttpServer = vertx.createHttpServer()
    val vertx = Vertx.vertx()
    vertx.createHttpServer().run {
        val router = vertx.router().apply {
            //hello
            get("/hello") { ctx ->
                ctx.response().end("Hello")
            }

            //default
            all { ctx ->
                val resp = ctx.response()
                resp.putHeader("Content-Type", "text/plain")
                resp.end("Index?")
            }
        }

        requestHandler { req -> router.accept(req) }.listen(8080)
    }
}
