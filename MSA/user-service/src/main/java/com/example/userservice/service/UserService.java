package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.User;
import com.example.userservice.vo.RequestUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User createUser(RequestUser requestUser);

    User getUserByUserId(String userId);
    Iterable<User> getUserByAll();

    UserDto getUserDetailsByEmail(String username);
}
