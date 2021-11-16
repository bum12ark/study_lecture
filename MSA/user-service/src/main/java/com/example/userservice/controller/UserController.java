package com.example.userservice.controller;

import com.example.userservice.vo.Greeting;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@Slf4j @RequiredArgsConstructor
public class UserController {

    private final Environment environment;

    private final Greeting greeting;

    @GetMapping("/welcome")
    public String welcome() {
//        greeting.getMessage();
        return environment.getProperty("greeting.message");
    }

    @GetMapping("/health_check")
    public String status() {
        return "It's Working in User Service";
    }

}
