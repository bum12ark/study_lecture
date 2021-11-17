package com.example.userservice.controller;

import com.example.userservice.entity.User;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/user-service")
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
        return String.format("It's Working in User Service on PORT %s",
                environment.getProperty("local.server.port"));
    }

    @PostMapping("/users")
    public ResponseEntity createUser(@RequestBody @Valid RequestUser requestUser) {
        User user = userService.createUser(requestUser);
        ResponseUser responseUser = new ResponseUser(user.getEmail(), user.getName(), user.getUserId());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @GetMapping("/users")
    public ResponseEntity getUsers() {
        Iterable<User> findUsers = userService.getUserByAll();

        List<ResponseUser> results = new ArrayList<>();
        findUsers.forEach(user -> results.add(new ResponseUser(user.getEmail(), user.getName(), user.getUserId(), new ArrayList<>())));

        return ResponseEntity.status(HttpStatus.OK).body(results);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity getUser(@PathVariable("userId") String userId) {

        User findUser = userService.getUserByUserId(userId);
        ResponseUser responseUser = new ResponseUser(findUser.getEmail(), findUser.getName(), findUser.getUserId());

        return ResponseEntity.status(HttpStatus.OK).body(responseUser);
    }
}
