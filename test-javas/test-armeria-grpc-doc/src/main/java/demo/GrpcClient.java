package demo;

import com.example.grpc.hello.Hello;
import com.example.grpc.hello.HelloServiceGrpc;
import com.linecorp.armeria.client.Clients;


public class GrpcClient {
    public static void main(String[] args) {
        HelloServiceGrpc.HelloServiceBlockingStub helloService = Clients.newClient(
                "gproto+http://127.0.0.1:8080/",
                HelloServiceGrpc.HelloServiceBlockingStub.class); // or HelloServiceFutureStub.class or HelloServiceStub.class

        Hello.HelloRequest request = Hello.HelloRequest.newBuilder().setName("Armerian World").build();
        Hello.HelloReply reply = helloService.hello(request);
        System.out.println(">>>>>" + reply.getMessage());
        assert reply.getMessage().equals("Hello, Armerian World!");
    }
}
