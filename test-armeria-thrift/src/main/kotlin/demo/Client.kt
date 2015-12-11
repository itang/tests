package demo

import com.example.thrift.HelloService
import com.linecorp.armeria.client.Clients

fun main(args: Array<String>) {
    val helloService: HelloService.Iface = Clients.newClient(
            "tbinary+http://127.0.0.1:8080/hello",
            HelloService.Iface::class.java
    ) // javaClass<HelloService.Iface>()

    val greeting: String = helloService.hello("Armerian World")
    println(greeting)

    time {
        println(helloService.hello("Kotlin"))
    }
}

fun time(t: () -> Unit): Unit {
    val start = System.currentTimeMillis()

    t()

    val end = System.currentTimeMillis()
    println("Elapsed time: ${((end - start) / 1000.0)} msecs")
}
