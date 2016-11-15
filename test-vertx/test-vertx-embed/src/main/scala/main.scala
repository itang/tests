import io.vertx.core.{ VertxOptions, Vertx }

object Main {

  def main(args: Array[String]): Unit = {
    val vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(40))

    val server = vertx.createHttpServer()
    server.requestHandler(request => {
      request.response().end("Hello world")
    })

    println("run vertx on :3000...")
    server.listen(3000)
  }
}
