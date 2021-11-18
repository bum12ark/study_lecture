package com.example.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDto {
    private String productId;
    private Integer qty;
    private Integer unitPrice;

    private String orderId;
    private String userId;
    private Integer totalPrice;
    private LocalDateTime createdAt;

    public OrderDto(String productId, Integer qty, Integer unitPrice) {
        this.productId = productId;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public OrderDto(String productId, Integer qty, Integer unitPrice, String orderId, String userId, Integer totalPrice, LocalDateTime createdAt) {
        this.productId = productId;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.orderId = orderId;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
    }
}
