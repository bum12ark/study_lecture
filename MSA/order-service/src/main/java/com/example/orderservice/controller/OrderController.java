package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.entity.Order;
import com.example.orderservice.messagequeue.KafkaProducer;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.vo.RequestOrder;
import com.example.orderservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order-service")
@RequiredArgsConstructor
public class OrderController {

    private final Environment environment;
    private final OrderService orderService;
    private final KafkaProducer kafkaProducer;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in Order Service on PORT %s",
                environment.getProperty("local.server.port"));
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity createOrder(@PathVariable("userId") String userId,
                                      @RequestBody RequestOrder requestOrder) {

        OrderDto orderDto = new OrderDto(requestOrder.getProductId(), requestOrder.getQty(), requestOrder.getUnitPrice());
        orderDto.setUserId(userId);

        Order order = orderService.createOrder(orderDto);

        ResponseOrder responseOrder = new ResponseOrder(order.getProductId(), order.getQty(), order.getUnitPrice(),
                order.getTotalPrice(), order.getCreatedAt(), order.getOrderId());

        kafkaProducer.send("example-category-topic", responseOrder);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity getOrder(@PathVariable("userId") String userId) {
        Iterable<Order> findOrdersByUserId = orderService.getOrdersByUserId(userId);

        List<ResponseOrder> results = new ArrayList<>();

        findOrdersByUserId.forEach(order -> {
            results.add(new ResponseOrder(order.getProductId(), order.getQty(), order.getUnitPrice(),
                    order.getTotalPrice(), order.getCreatedAt(), order.getOrderId()));
        });

        return ResponseEntity.status(HttpStatus.OK).body(results);
    }
}
