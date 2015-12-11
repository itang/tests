package impl.service

import java.util.Date

import com.example.thrift.HelloService
import org.apache.thrift.async.AsyncMethodCallback

import util.strfmt

class MyHelloService : HelloService.Iface {
    override fun hello(name: String): String {
        return "Hello, $name! at ${Date().strfmt()}"
    }
}

class MyHelloServiceAsync : HelloService.AsyncIface {
    override fun hello(name: String, resultHandler: AsyncMethodCallback<Any>) {
        resultHandler.onComplete("Hello, $name! at ${Date().strfmt()}")
    }
}
