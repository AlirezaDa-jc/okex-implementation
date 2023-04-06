package com.example.demo.okex.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * Created By Alireza Dolatabadi
 * Date: 3/27/2023
 * Time: 7:35 PM
 */
@Data
@ToString
@AllArgsConstructor
public class OkexResponse {
    private String msg;
    private List<Object> data;
    private int code;
}
