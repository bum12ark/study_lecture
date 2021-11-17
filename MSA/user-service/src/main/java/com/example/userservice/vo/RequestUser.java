package com.example.userservice.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RequestUser {
    @NotNull(message = "Email can't be null")
    @Size(min = 2, message = "Email not be less than 2 characters")
    @Email
    private String email;
    @NotNull(message = "Name can't be null")
    @Size(min = 2, message = "Name not be less than 2 characters")
    private String name;
    @NotNull(message = "Password can't be null")
    @Size(min = 8, message = "Password not be less than 8 characters")
    private String pwd;
}
