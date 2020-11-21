package com.example.demo;

import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.io.IOException;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Autowired
    private UserServiceImpl userService;


    @PostConstruct
    public void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("post construct");
                System.out.println("grpc listen on :9090");
                io.grpc.Server server = ServerBuilder.forPort(9090).addService(userService).build();
                // 服务的启动：
                try {
                    server.start();

                    // 服务的关闭：
                    server.awaitTermination();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
