import java.net.URI

import ratpack.server.{RatpackServer, ServerConfig}

object Main {

  def main(args: Array[String]): Unit = {
    RatpackServer.start(server => server
      .serverConfig(ServerConfig.embedded().publicAddress(new URI("http://company.org")))
      .registryOf(registry => registry.add("World!"))
      .handlers(chain => chain
        .get { ctx => ctx.render("Hello " + ctx.get(classOf[String])) }
        .get(":name", ctx => ctx.render("Hello " + ctx.getPathTokens().get("name") + "!"))
      )
    )
  }

}


