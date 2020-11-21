package com.example.demo;

import io.grpc.Grpc;
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import test.UserGrpc;
import test.UserOuterClass;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class UserController {

    @Data
    @Builder
    public static class Resp {
        private int code;
        private String msg;
    }

    @GetMapping("/")
    public Resp test(@RequestParam(defaultValue = "itang") String username,
                     @RequestParam(defaultValue = "123456") String password) {
        System.out.println("request: " + username + " " + password);

        ManagedChannel channel = NettyChannelBuilder.forAddress("127.0.0.1", 9090).usePlaintext().build();

        UserGrpc.UserBlockingStub stub = UserGrpc.newBlockingStub(channel);

        UserOuterClass.APIResponse ret = stub.login(UserOuterClass.LoginRequest.newBuilder().setPassword(password).setUsername(username).build());

        try {
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return Resp.builder().code(ret.getResponseCode()).msg(ret.getResponsemessage()).build();
    }
}
