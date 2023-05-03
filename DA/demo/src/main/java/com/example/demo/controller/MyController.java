package com.example.demo.controller;

import com.example.demo.feign.FlaskClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    private final FlaskClient flaskClient;

    @Autowired
    public MyController(FlaskClient flaskClient) {
        this.flaskClient = flaskClient;
    }

    @GetMapping("/hello")
    public String sayHello(@RequestParam("name") String name) {
        return flaskClient.sayHello(name);
    }

}

