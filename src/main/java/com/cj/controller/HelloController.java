package com.cj.controller;

import com.cj.annotation.RateLimiter;
import com.cj.annotation.RepeatSubmit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    @RateLimiter(time = 10,count = 3)
    public String hello() {

        return "hello";
    }

    /**
     * @RequestBody 底层  IO流获取
     * @param json
     * @return
     */
    @RepeatSubmit(interval = 10000)
    @GetMapping("/hello1")
    public String hello1(@RequestBody String json) {


        return json;
    }


}
