package com.example.userservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseUser {
    private String email;
    private String name;
    private String userId;

    private List<ResponseUser> orders;

    public ResponseUser(String email, String name, String userId) {
        this.email = email;
        this.name = name;
        this.userId = userId;
    }

    public ResponseUser(String email, String name, String userId, List<ResponseUser> orders) {
        this.email = email;
        this.name = name;
        this.userId = userId;
        this.orders = orders;
    }
}
