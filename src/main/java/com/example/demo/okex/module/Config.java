package com.example.demo.okex.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * Created By Alireza Dolatabadi
 * Date: 3/27/2023
 * Time: 6:50 PM
 */
@AllArgsConstructor
@Data
@ToString
public class Config {
    private String apiKey;
    private String secretKey;
    private String passPhrase;

}
