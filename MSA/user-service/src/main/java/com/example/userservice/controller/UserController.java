package com.example.userservice.controller;

import com.example.userservice.entity.User;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.Greeting;
import com.example.userservice.vo.RequestUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;


@RestController
@RequestMapping("/")
@Slf4j @RequiredArgsConstructor
public class UserController {

//    private final Greeting greeting;
    private final Environment environment;
    private final UserService userService;

    @GetMapping("/welcome")
    public String welcome() {
//        greeting.getMessage();
        return environment.getProperty("greeting.message");
    }

    @GetMapping("/health_check")
    public String status() {
        return "It's Working in User Service";
    }

    @PostMapping("/users")
    public ResponseEntity createUser(@RequestBody @Valid RequestUser requestUser) {
        log.info("UserController.createUser");

        User user = userService.createUser(requestUser);
        ResponseUser responseUser = new ResponseUser(user.getEmail(), user.getName(), user.getUserId());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @AllArgsConstructor @Data
    public static class ResponseUser {
        private String email;
        private String name;
        private String userId;
    }
}
