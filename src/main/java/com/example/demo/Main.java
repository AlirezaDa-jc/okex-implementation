package com.example.demo;

import com.example.demo.okex.module.Config;
import com.example.demo.okex.module.OkexResponse;
import com.example.demo.okex.services.ApiService;
import com.example.demo.okex.services.impl.ApiServiceImpl;

import java.io.IOException;
import java.util.Map;

/**
 * Created By Alireza Dolatabadi
 * Date: 3/27/2023
 * Time: 6:45 PM
 */
public class Main {
    public static void main(String[] args) throws IOException {
        ApiService apiService = new ApiServiceImpl(
                new Config(
                        "...",
                        "...",
                        "..."
                )
        );

        String instType = "SPOT";
        //SPOT
        //MARGIN
        //SWAP
        //FUTURES
        //OPTION

        // if InstType is Option YOu Should send Uly or instFamily too .
        //uly	String	Conditional	Underlying
        //Only applicable to FUTURES/SWAP/OPTION.If instType is OPTION, either uly or instFamily is required.
        //instFamily	String	Conditional	Instrument family
        //Only applicable to FUTURES/SWAP/OPTION. If instType is OPTION, either uly or instFamily is required.
        OkexResponse response = apiService.getInstrument(instType);
        System.err.println("getInstrument");
        System.out.println(response);

        response = apiService.getCurrencies();
        System.err.println("getCurrencies");
        System.out.println(response);

        response = apiService.placeOrder();
        System.err.println("placeOrder");
        System.out.println(response);

        Map order = (Map) response.getData().get(0);
        response = apiService.getOrderDetails(order.get("ordId").toString(), "BTC-USDT");
        System.err.println("getOrderDetails");
        System.out.println(response);

        response = apiService.getPendingOrders();
        System.err.println("getPendingOrders");
        System.out.println(response);

        response = apiService.getOrdersHistoryLast7Days(instType);
        System.err.println("getOrdersHistoryLast7Days");
        System.out.println(response);

        response = apiService.getOrdersHistoryLast3Months(instType);
        System.err.println("getOrdersHistoryLast3Months");
        System.out.println(response);

        //If You Want to cancel Your Order After Test Uncomment Below
//        apiService.cancelOrder(response.getData().get(0).get("ordId").toString());
//        System.out.println(response);

        response = apiService.transferFunds();
        System.err.println("transferFunds");
        System.out.println(response);

        response = apiService.getDepositAddress();
        System.err.println("getDepositAddress");
        System.out.println(response);

        response = apiService.getDepositHistory();
        System.err.println("getDepositHistory");
        System.out.println(response);

        response = apiService.createWithdrawRequest();
        System.err.println("createWithdrawRequest");
        System.out.println(response);

        //If You Want to cancel Your Withdraw Request After Test Uncomment Below
//        response = apiService.cancelWithdrawRequest(response.getData().get(0).get("wdId").toString());
//        System.out.println(response);

        response = apiService.getWithdrawHistory();
        System.err.println("getWithdrawHistory");
        System.out.println(response);

        response = apiService.getTradingAccountBalance();
        System.err.println("getTradingAccountBalance");
        System.out.println(response);

        response = apiService.getFundingAccountBalance();
        System.err.println("getFundingAccountBalance");
        System.out.println(response);

        response = apiService.createAlgoOrder();
        System.err.println("createAlgoOrder");
        System.out.println(response);

        order = (Map) response.getData().get(0);
        response = apiService.getAlgoOrderDetails(order.get("algoId").toString());
        System.err.println("getAlgoOrderDetails");
        System.out.println(response);

        String ordType = "conditional";
        //conditional: One-way stop order
        //oco: One-cancels-the-other order
        //trigger: Trigger order
        //move_order_stop: Trailing order
        //iceberg: Iceberg order
        //twap: TWAP order
        response = apiService.getPendingAlgoOrders(ordType);
        System.err.println("getPendingAlgoOrders");
        System.out.println(response);

//        response = apiService.cancelAlgoOrder(response.getData().get(0).get("algoId").toString(), "BTC-USDT");
//        System.err.println("cancelAlgoOrder");
//        System.out.println(response);

        String state = "canceled";
        //effective
        //canceled
        //order_failed
        response = apiService.getHistoryAlgoOrders(ordType, state);
        System.err.println("getHistoryAlgoOrders");
        System.out.println(response);

        response = apiService.getMarketCandles("BTC-USDT");
        System.err.println("getMarketCandles");
        System.out.println(response);

    }
}
