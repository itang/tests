package demo

import com.example.thrift.HelloService
import com.linecorp.armeria.common.SerializationFormat
import com.linecorp.armeria.common.SessionProtocol
import com.linecorp.armeria.server.Server
import com.linecorp.armeria.server.ServerBuilder
import com.linecorp.armeria.server.thrift.ThriftService
import impl.service.MyHelloServiceAysnc

fun main(args: Array<String>) {
    val helloHandler: HelloService.AsyncIface = MyHelloServiceAysnc()

    val sb: ServerBuilder = ServerBuilder()
    sb.port(8080, SessionProtocol.HTTP)
    sb.serviceAt("/hello", ThriftService.of(helloHandler, SerializationFormat.THRIFT_BINARY)).build()
    // .decorate(x -> LoggingService(x)))
    //.serviceUnder("/docs/", DocService()).build()

    val server: Server = sb.build()
    server.start()
}
