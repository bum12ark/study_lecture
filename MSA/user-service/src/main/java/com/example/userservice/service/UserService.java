package com.example.userservice.service;

import com.example.userservice.entity.User;
import com.example.userservice.vo.RequestUser;

public interface UserService {

    User createUser(RequestUser requestUser);

    User getUserByUserId(String userId);
    Iterable<User> getUserByAll();
}
