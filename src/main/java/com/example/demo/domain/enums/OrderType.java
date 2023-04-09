package com.example.demo.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created By Alireza Dolatabadi
 * Date: 4/9/2023
 * Time: 6:54 PM
 */

/**
 * market: Market order
 * limit: Limit order
 * post_only: Post-only order
 * fok: Fill-or-kill order
 * ioc: Immediate-or-cancel order
 * optimal_limit_ioc: Market order with immediate-or-cancel order (applicable only to Futures and Perpetual swap).
 */
@AllArgsConstructor
@Getter
public enum OrderType {
    LIMIT("limit"),
    MARKET("market"),
    POST_ONLY("post_only"),
    FOK("fok"),
    IOC("ioc"),
    OPTIMAL_LIMIT_IOC("optimal_limit_ioc");

    private String type;
}
