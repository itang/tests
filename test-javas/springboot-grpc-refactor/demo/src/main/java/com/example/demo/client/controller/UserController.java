package com.example.demo.client.controller;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import test.UserGrpc;
import test.UserOuterClass;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserGrpc.UserBlockingStub userBlockingStub;

    @GetMapping("/")
    public Resp test(@RequestParam(defaultValue = "itang") String username,
                     @RequestParam(defaultValue = "123456") String password) {
        log.info("request: {}, {}", username, password);

        UserOuterClass.APIResponse ret = userBlockingStub.login(UserOuterClass.LoginRequest.newBuilder().setPassword(password).setUsername(username).build());

        return Resp.builder().code(ret.getResponseCode()).msg(ret.getResponsemessage()).build();
    }

    @Data
    @Builder
    public static class Resp {
        private int code;
        private String msg;
    }
}
