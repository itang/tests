package demo

import com.github.kittinunf.fuel.httpGet
import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import java.time.Duration

fun main(args: Array<String>) {
    val PORT = 8002
    Thread({
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

        println("listen $PORT...")
        val server = vertx.createHttpServer().apply { requestHandler({ req -> router.accept(req) }).listen(PORT) }
        Runtime.getRuntime().addShutdownHook(object : Thread() {
            override fun run() {
                server.close()
            }
        })

    }).apply { this.isDaemon = true }.start()

    Thread.sleep(Duration.ofSeconds(3).toMillis())

    val urls = listOf("/ok", "/timeout", "/500", "/400", "/exception")
    for (url in urls) {
        val (req, resp, result) = "http://localhost:$PORT/$url".httpGet()
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
    System.exit(0)
}
