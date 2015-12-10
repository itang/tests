package impl.service

import com.example.thrift.HelloService
import org.apache.thrift.async.AsyncMethodCallback

class MyHelloService : HelloService.Iface {
    override fun hello(name: String): String {
        return "Hello, $name!"
    }
}

class MyHelloServiceAsync : HelloService.AsyncIface {
    override fun hello(name: String, resultHandler: AsyncMethodCallback<Any>) {
        resultHandler.onComplete("Hello, $name!")
    }
}
