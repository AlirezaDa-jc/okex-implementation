package com.example.demo.okex.services;

import com.example.demo.domain.dtoes.PlaceOrder;
import com.example.demo.okex.module.OkexResponse;

import java.io.IOException;

/**
 * Created By Alireza Dolatabadi
 * Date: 3/27/2023
 * Time: 6:45 PM
 */
public interface ApiService {
    OkexResponse placeOrder(PlaceOrder placeOrder) throws IOException;

    OkexResponse getOrderDetails(String ordId, String instId) throws IOException;

    OkexResponse cancelOrder(String ordId) throws IOException;

    OkexResponse transferFunds() throws IOException;

    OkexResponse getDepositAddress() throws IOException;

    OkexResponse getDepositHistory() throws IOException;

    OkexResponse createWithdrawRequest() throws IOException;

    OkexResponse getWithdrawHistory() throws IOException;

    OkexResponse cancelWithdrawRequest(String wdId) throws IOException;

    OkexResponse getTradingAccountBalance() throws IOException;

    OkexResponse getFundingAccountBalance() throws IOException;

    OkexResponse createAlgoOrder() throws IOException;

    OkexResponse getAlgoOrderDetails(String algoId) throws IOException;

    OkexResponse cancelAlgoOrder(String algoId, String instId) throws IOException;

    OkexResponse getPendingAlgoOrders(String ordType) throws IOException;

    OkexResponse getHistoryAlgoOrders(String ordType, String state) throws IOException;

    OkexResponse getPendingOrders() throws IOException;

    OkexResponse getOrdersHistoryLast7Days(String instType) throws IOException;

    OkexResponse getOrdersHistoryLast3Months(String instType) throws IOException;

    OkexResponse getInstrument(String instType) throws IOException;

    OkexResponse getCurrencies() throws IOException;

    OkexResponse getMarketCandles(String instId) throws IOException;

    OkexResponse getTickerPrice(String instId) throws IOException;
}
