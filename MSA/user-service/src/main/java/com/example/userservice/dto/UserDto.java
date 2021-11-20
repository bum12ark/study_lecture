package com.example.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data @AllArgsConstructor
public class UserDto {
    private String email;
    private String name;
    private String userId;
    private String encryptedPwd;
}
