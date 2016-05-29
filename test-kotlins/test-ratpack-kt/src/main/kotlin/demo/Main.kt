package demo

import ratpack.http.ResponseChunks
import ratpack.server.RatpackServer
import ratpack.stream.Streams
import java.time.Duration

fun main(args: Array<String>) {
    RatpackServer.start { b ->
        b.handlers { chain ->
            chain.get { ctx ->
                ctx.render("Hello from ratpack + Kotlin")
            }

            chain.get("stream") { ctx ->
                val publisher = Streams.periodically(ctx, Duration.ofMillis(1000)) { i ->
                    if (i < 10) i.toString() else null
                }
                ctx.render(ResponseChunks.stringChunks(publisher))
            }
        }
    }
}

