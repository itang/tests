package demo

import com.example.thrift.HelloService
import com.linecorp.armeria.client.Clients

import util.time
import util.loop

fun main(args: Array<String>) {
    val helloService: HelloService.Iface = Clients.newClient(
            "tbinary+http://127.0.0.1:8080/hello",
            HelloService.Iface::class.java
    ) // javaClass<HelloService.Iface>()

    val greeting: String = time { helloService.hello("Armerian World") }
    println(greeting)

    time {
        println(helloService.hello("Kotlin"))
    }

    1000.loop { i ->
        println(helloService.hello("$i: Kotlin"))
        Thread.sleep(1000)
    }
}

