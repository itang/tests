package com.example.demo.data;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class Data {
    @lombok.Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request1 {
        String name;
        Integer age;
    }

    @lombok.Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request2 {
        String name;
        String name2;
        Integer age2;
    }

    @lombok.Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Resp {
        private String msg;
        private Object req;
    }
}
