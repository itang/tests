package demo

import com.example.thrift.HelloService
import com.linecorp.armeria.common.SerializationFormat
import com.linecorp.armeria.common.SessionProtocol
import com.linecorp.armeria.server.Server
import com.linecorp.armeria.server.ServerBuilder
import com.linecorp.armeria.server.docs.DocService
import com.linecorp.armeria.server.logging.LoggingService
import com.linecorp.armeria.server.thrift.ThriftService
import impl.service.MyHelloServiceAsync

fun main(args: Array<String>) {
    val helloHandler: HelloService.AsyncIface = MyHelloServiceAsync()

    val server: Server = ServerBuilder()
            .port(8080, SessionProtocol.HTTP)
            .serviceAt("/hello", ThriftService.of(helloHandler, SerializationFormat.THRIFT_BINARY)/*.decorate { (x: Service) -> LoggingService(x) }*/)
            .serviceUnder("/docs/", DocService()).build()

    server.start()
}
