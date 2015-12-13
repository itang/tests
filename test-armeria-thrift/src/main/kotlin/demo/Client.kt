package demo

import com.example.thrift.HelloService
import com.linecorp.armeria.client.Clients
import util.loop
import util.tap
import util.time

fun main(args: Array<String>) {
    val helloService: HelloService.Iface = Clients.newClient(
            "tbinary+http://127.0.0.1:8080/hello",
            HelloService.Iface::class.java
    ) // javaClass<HelloService.Iface>()

    val (greeting: String, elapsed: Double) = time { helloService.hello("Armerian World") }
    println("$greeting Elapsed: $elapsed")

    1000.loop { i ->
        time {
            helloService.hello("$i: Kotlin")
        }.tap { p ->
            println("${p.first} Elapsed: ${p.second}")
        }

        Thread.sleep(1000)
    }
}
