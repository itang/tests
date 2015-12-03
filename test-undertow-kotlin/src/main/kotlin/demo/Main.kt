package demo

import io.undertow.Undertow
import io.undertow.util.Headers

fun main(args: Array<String>) {
    val server = Undertow.builder()
            .addHttpListener(8080, "localhost")
            .setHandler {
                it.responseHeaders.put(Headers.CONTENT_TYPE, "text/plain")
                it.responseSender.send("Hello World")
            }.build()
    server.start()
}
