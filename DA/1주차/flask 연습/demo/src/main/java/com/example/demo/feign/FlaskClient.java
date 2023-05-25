package com.example.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "flask", url = "http://localhost:5000")
public interface FlaskClient {

    @GetMapping("/hello")
    String sayHello(@RequestParam("name") String name);

}

