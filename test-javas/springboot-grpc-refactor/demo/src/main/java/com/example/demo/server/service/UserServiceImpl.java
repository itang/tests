package com.example.demo.server.service;

import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;
import test.UserGrpc;
import test.UserOuterClass;

@Service
public class UserServiceImpl extends UserGrpc.UserImplBase {
    @Override
    public void login(test.UserOuterClass.LoginRequest request, StreamObserver<test.UserOuterClass.APIResponse> responseObserver) {
        String username = request.getUsername();
        String password = request.getPassword();

        UserOuterClass.APIResponse.Builder response = UserOuterClass.APIResponse.newBuilder();
        // APIResponse对应.proto里对应的一个message的名字

        if (username.equals(password)) {
            response.setResponseCode(0).setResponsemessage("SUCCESS");
        } else {
            response.setResponseCode(100).setResponsemessage("INVALID PASSWD");
        }

        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    @Override
    public void logout(test.UserOuterClass.Empty request, StreamObserver<test.UserOuterClass.APIResponse> responseObserver) {
        super.logout(request, responseObserver);
    }
}
