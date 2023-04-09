package com.example.demo.domain.dtoes;

import com.example.demo.domain.enums.OrderSide;
import com.example.demo.domain.enums.OrderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created By Alireza Dolatabadi
 * Date: 4/9/2023
 * Time: 7:04 PM
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlaceOrder {
    @NotNull
    @NotBlank
    private String instId;
    @NotNull
    private Double size;
    @NotNull
    private Double unitPrice;
    @NotNull
    @NotBlank
    private OrderSide side;
    @NotNull
    @NotBlank
    private OrderType type;
}
