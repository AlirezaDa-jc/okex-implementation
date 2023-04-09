package com.example.demo.controller;

import com.example.demo.domain.Order;
import com.example.demo.domain.dtoes.PlaceOrder;
import com.example.demo.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created By Alireza Dolatabadi
 * Date: 4/9/2023
 * Time: 7:02 PM
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody  PlaceOrder placeOrder) throws IOException {
        Order order = orderService.createOrder(placeOrder);
        return ResponseEntity.ok(order);
    }
}
