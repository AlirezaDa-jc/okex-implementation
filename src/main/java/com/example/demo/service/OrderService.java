package com.example.demo.service;

import com.example.demo.domain.Order;
import com.example.demo.domain.dtoes.PlaceOrder;

import java.io.IOException;

/**
 * Created By Alireza Dolatabadi
 * Date: 4/9/2023
 * Time: 7:09 PM
 */
public interface OrderService {
    Order createOrder(PlaceOrder placeOrder) throws IOException;
}
