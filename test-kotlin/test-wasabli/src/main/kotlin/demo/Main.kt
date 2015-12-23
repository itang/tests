package demo

import org.wasabi.app.AppConfiguration
import org.wasabi.app.AppServer

fun main(args: Array<String>) {
    val config = AppConfiguration(enableLogging = false, port = 3000)
    var server = AppServer(config)

    // server.get("/", { response.send("Hello World!") })

    server.get("/", { context -> context.response.send("Hello World!") })

    server.start()
}
