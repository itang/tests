package com.example.demo;

import com.example.demo.data.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@SpringBootApplication
@RestController
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    @GetMapping("/test1")
    @ResponseBody
    public Data.Resp test1(Data.Request1 request1, Data.Request2 request2) {
        return new Data.Resp("hello", Arrays.asList(request1, request2));
    }

    /*
    post http://localhost:8080/test2?name=tqibm
{
  "name": "itang", "age": 1
}

     */
    @PostMapping("/test2")
    @ResponseBody
    public Data.Resp test2(Data.Request1 request1/* 来自于请求参数*/, @RequestBody Data.Request2 request2) {
        /*
        {
    "msg": "hello",
    "req": [
        {
            "name": "tqibm",
            "age": null
        },
        {
            "name": "itang",
            "name2": null,
            "age2": null
        }
    ]
}
         */
        return new Data.Resp("hello", Arrays.asList(request1, request2));
    }


    /*
post http://localhost:8080/test3?name=tqibm
{
"name": "itang", "age": 1
}

 */
    @PostMapping("/test3")
    @ResponseBody
    public Data.Resp test3(@RequestParam("request1") Data.Request1 request1/* 来自于请求参数 request1[name], request1[age]*/, @RequestBody Data.Request2 request2) {
        /*
        {
    "msg": "hello",
    "req": [
        {
            "name": "tqibm",
            "age": null
        },
        {
            "name": "itang",
            "name2": null,
            "age2": null
        }
    ]
}
         */
        return new Data.Resp("hello", Arrays.asList(request1, request2));
    }
}
