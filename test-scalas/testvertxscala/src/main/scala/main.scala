import io.vertx.core.{Vertx, VertxOptions}
import io.vertx.ext.web.Router

object Server extends App {
  val opts = new VertxOptions()
  val vertx = Vertx.vertx(opts)
  val server = vertx.createHttpServer()

  val router = Router.router(vertx)

  router.route.handler(ctx => {
    // This handler will be called for every request
    val response = ctx.response()
    response.putHeader("content-type", "text/plain")

    // Write to the response and end it
    response.end("Hello World from Vert.x-Web!")
  })

  server.requestHandler(router.accept _).listen(8080)
}
