package com.example.orderservice.service;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.entity.Order;
import com.example.orderservice.vo.RequestOrder;

public interface OrderService {
    Order createOrder(OrderDto orderDto);
    Order getOrderByOrderId(String orderId);
    Iterable<Order> getOrdersByUserId(String userId);
}
