package com.example.demo.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created By Alireza Dolatabadi
 * Date: 4/9/2023
 * Time: 6:53 PM
 */
@AllArgsConstructor
@Getter
public enum OrderSide {
    SELL("sell"),
    BUY("buy");

    private final String side;
}
