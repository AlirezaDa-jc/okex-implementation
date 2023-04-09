package com.example.demo.utills;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created By Alireza Dolatabadi
 * Date: 4/9/2023
 * Time: 7:51 PM
 */
public class ReusableMethods {
    /*
        Due to Faulty Calculation In Double By Java we use This method
     */
    public static double operationDouble(Double num1, Double num2, char operation) {
        BigDecimal bigDecimal = new BigDecimal(num1.toString());
        BigDecimal bigDecimal2 = new BigDecimal(num2.toString());
        if (operation == '-') {
            return bigDecimal.subtract(bigDecimal2).doubleValue();
        } else if (operation == '+') {
            return bigDecimal.add(bigDecimal2).doubleValue();
        } else if (operation == '/') {
            return bigDecimal.divide(bigDecimal2, 10, RoundingMode.HALF_EVEN).doubleValue();
        } else if (operation == '*') {
            return bigDecimal.multiply(bigDecimal2).doubleValue();
        }
        return 0;
    }
}
