package demo

import com.github.kittinunf.fuel.httpGet
import io.vertx.core.CompositeFuture
import io.vertx.core.Future
import io.vertx.core.Vertx
import io.vertx.core.http.HttpServer
import io.vertx.ext.web.Router
import java.time.Duration

object GlobalConfig {
    val PORT = 8002
}

fun main(args: Array<String>) {
    //Thread({
    val vertx = Vertx.factory.vertx()

    val router = Router.router(vertx).apply {
        get("/ok").blockingHandler { ctx ->
            ctx.response().end("ok")
        }

        get("/timeout").blockingHandler { ctx ->
            Thread.sleep(Duration.ofSeconds(3).toMillis())
        }

        get("/500").handler { ctx ->
            ctx.fail(500)

        }

        get("/400").handler { ctx ->
            ctx.fail(400)
        }

        get("/exception").handler { ctx ->
            throw RuntimeException("exception")
        }
    }

    println("listen ${GlobalConfig.PORT}...")
    val httpServerFuture = Future.future<HttpServer>()
    val server = vertx.createHttpServer().requestHandler { req -> router.accept(req) }.listen(GlobalConfig.PORT, httpServerFuture.completer())

    Runtime.getRuntime().addShutdownHook(object : Thread() {
        override fun run() {
            server.close()
        }
    })

//}).apply { this.isDaemon = true }.start()

//Thread.sleep(Duration.ofSeconds(3).toMillis())
    CompositeFuture.all(listOf(httpServerFuture)).setHandler { ar ->
        if (ar.succeeded()) {
            println("http服务器启动...")
            val urls = listOf("/ok", "/timeout", "/500", "/400", "/exception")
            for (url in urls) {
                val (req, resp, result) = "http://localhost:${GlobalConfig.PORT}/$url".httpGet()
                        .timeout(Duration.ofSeconds(1).toMillis().toInt())
                        .timeoutRead(Duration.ofSeconds(2).toMillis().toInt())
                        .responseString()
                println(result)

                val (value, error) = result
                println("value: $value")
                if (error != null) {
                    println(error.javaClass)
                    println("message: ${error.message}")
                    println(error.exception)
                }

                println("*".repeat(100))
                println("*".repeat(100))
            }
        } else {
            println("start http server error！！！")
        }

        System.exit(0)
    }

}
