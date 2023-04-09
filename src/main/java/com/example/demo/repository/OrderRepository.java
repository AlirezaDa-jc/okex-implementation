package com.example.demo.repository;

import com.example.demo.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By Alireza Dolatabadi
 * Date: 4/9/2023
 * Time: 7:38 PM
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
}
