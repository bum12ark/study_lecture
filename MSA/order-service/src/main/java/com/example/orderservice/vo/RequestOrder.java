package com.example.orderservice.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data @AllArgsConstructor
public class RequestOrder {
    private String productId;
    private Integer qty;
    private Integer unitPrice;
}
