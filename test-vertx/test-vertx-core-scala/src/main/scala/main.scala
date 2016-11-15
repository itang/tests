
import io.vertx.core._
import io.vertx.core.http.HttpServer
import java.time.Duration
import java.util.Arrays.asList
import java.util.concurrent.atomic.AtomicLong

//import collection.JavaConverters._
import scalaj.http._

object GlobalState {
  val count = new AtomicLong
}

object GlobalConfig {
  val PORT = 8080
}

object Main {
  def main(args: Array[String]): Unit = {
    val vertx = Vertx.vertx(/*VertxOptions().setEventLoopPoolSize(4).setWorkerPoolSize(4)*/)

    vertx.setPeriodic(Duration.ofSeconds(1).toMillis, { id =>
      println(s"count ${GlobalState.count.getAndIncrement}")
    })

    val httpServerFuture = Future.future[HttpServer]
    vertx.createHttpServer().requestHandler { ctx =>
      println(Thread.currentThread)
      //Thread.sleep(1000 * 20)//warning
      val count = ctx.getParam("count")
      ctx.response.end(s"Hello, World $count")
    }.listen(GlobalConfig.PORT, httpServerFuture.completer)

    //val list = List(httpServerFuture).asJava.asInstanceOf[java.util.List[Future[_]]]
    CompositeFuture.all(asList(httpServerFuture)).setHandler({ ar =>
      if (ar.succeeded) {
        println("http server start success.")
        vertx.setPeriodic(Duration.ofSeconds(2).toMillis, { id =>
          vertx.executeBlocking({ f: Future[String] =>
            val ret = Http(s"http://localhost:${GlobalConfig.PORT}").param("count", GlobalState.count.get().toString).asString
            f.complete(ret.body)
          }, { res: AsyncResult[String] => println(res.result) })
        })
      } else {
        println("http server start failed!")
      }
    })
  }
}
