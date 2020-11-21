package com.example.demo.server;

import com.example.demo.server.service.UserServiceImpl;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
@Slf4j
public class GrpcServerImpl {
    @Autowired
    private UserServiceImpl userService;

    @Value("${grpc.server.port:9090}")
    private int port;

    @PostConstruct
    public void init() {
        new Thread(this::startBlocking).start();
    }

    private void startBlocking() {
        log.info("gRPC server listen on :{}", port);
        io.grpc.Server server = ServerBuilder.forPort(port).addService(userService).build();

        try {
            // 服务的启动：
            server.start();

            // 服务的关闭：
            server.awaitTermination();
        } catch (IOException | InterruptedException e) {
            log.error("grpc server error", e);
        }
    }
}
