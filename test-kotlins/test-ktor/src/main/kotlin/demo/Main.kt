package demo

import org.jetbrains.ktor.netty.*
import org.jetbrains.ktor.routing.*
import org.jetbrains.ktor.application.*
import org.jetbrains.ktor.host.*
import org.jetbrains.ktor.http.*
import org.jetbrains.ktor.response.*

fun main(args: Array<String>) {
    embeddedServer(Netty, 8080){
        routing {
            get("/") {
                call.respondText("Hello, World", ContentType.Text.Html)
            }
        }
    }.start(wait = true)
}

