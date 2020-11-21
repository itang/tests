package com.example.demo.client.config;

import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import test.UserGrpc;

@Configuration
public class UserClientConfig {

    @Value("${grpc.server.host:127.0.0.1}")
    private String host;

    @Value("${grpc.server.port:9090}")
    private int port;

    @Bean
    public UserGrpc.UserBlockingStub userBlockingStub() {
        ManagedChannel channel = NettyChannelBuilder.forAddress(host, port).usePlaintext().build();
        return UserGrpc.newBlockingStub(channel);
    }
}
