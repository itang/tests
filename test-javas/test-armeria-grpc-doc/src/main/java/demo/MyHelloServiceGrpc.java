package demo;

import com.example.grpc.hello.Hello;
import io.grpc.stub.StreamObserver;

public class MyHelloServiceGrpc extends com.example.grpc.hello.HelloServiceGrpc.HelloServiceImplBase {
    @Override
    public void hello(Hello.HelloRequest request, StreamObserver<Hello.HelloReply> responseObserver) {
        Hello.HelloReply reply = Hello.HelloReply.newBuilder().setMessage("Hello, " + request.getName()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
