/**
  * main.scala
  */

//import scatang._
import ratpack.server.RatpackServer
import java.time.Duration

import ratpack.handling.{Context, Handler}
import ratpack.http.ResponseChunks
import ratpack.registry.Registry
import ratpack.stream.{Streams, TransformablePublisher}

object Main extends App {
    RatpackServer.start { it =>
        it.handlers { chain =>
            chain.get { ctx =>
                ctx.render("Hello from ratpack + Scala")
            }

            chain.get("/stream", (ctx: Context) => {
                val publisher: TransformablePublisher[String] = Streams.periodically(ctx.asInstanceOf[Registry], Duration.ofMillis(1000), (i: Integer) => {
                    if (i < 10) i.toString else null
                })
                ctx.render(ResponseChunks.stringChunks(publisher))
            })
        }
    }
}


