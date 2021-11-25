package com.example.userservice.dto;

import com.example.userservice.vo.ResponseOrder;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Data @AllArgsConstructor
public class UserDto {
    private String email;
    private String name;
    private String userId;
    private String encryptedPwd;

    private List<ResponseOrder> orders;

    public UserDto(String email, String name, String userId, String encryptedPwd) {
        this.email = email;
        this.name = name;
        this.userId = userId;
        this.encryptedPwd = encryptedPwd;
    }
}
