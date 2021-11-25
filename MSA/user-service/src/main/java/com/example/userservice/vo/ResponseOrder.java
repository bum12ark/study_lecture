package com.example.userservice.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @AllArgsConstructor @NoArgsConstructor
public class ResponseOrder {

    private String productId;
    private Integer unitPrice;
    private Integer totalPrice;
    private LocalDateTime createAt;

    private String orderId;
}
