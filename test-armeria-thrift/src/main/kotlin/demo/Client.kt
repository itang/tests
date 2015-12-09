package demo

import com.example.thrift.HelloService
import com.linecorp.armeria.client.Clients

fun main(args: Array<String>) {
    val helloService: HelloService.Iface = Clients.newClient(
            "tbinary+http://127.0.0.1:8080/hello",
            javaClass<HelloService.Iface>()); // or AsyncIface.class

    val greeting: String = helloService.hello("Armerian World")
    println(greeting)
}
