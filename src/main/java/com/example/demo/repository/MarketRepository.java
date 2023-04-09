package com.example.demo.repository;

import com.example.demo.domain.Market;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created By Alireza Dolatabadi
 * Date: 4/9/2023
 * Time: 7:25 PM
 */
public interface MarketRepository extends JpaRepository<Market,Long> {
    Optional<Market> findByInstId(String instId);
}
