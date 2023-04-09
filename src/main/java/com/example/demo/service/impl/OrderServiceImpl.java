package com.example.demo.service.impl;

import com.example.demo.domain.Market;
import com.example.demo.domain.Order;
import com.example.demo.domain.dtoes.PlaceOrder;
import com.example.demo.domain.enums.OrderSide;
import com.example.demo.okex.module.Config;
import com.example.demo.okex.module.OkexResponse;
import com.example.demo.okex.services.ApiService;
import com.example.demo.okex.services.impl.ApiServiceImpl;
import com.example.demo.repository.MarketRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.OrderService;
import com.example.demo.tasks.TimerTaskOkex;
import com.example.demo.utills.ReusableMethods;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Timer;

/**
 * Created By Alireza Dolatabadi
 * Date: 4/9/2023
 * Time: 7:12 PM
 */
@Service
public class OrderServiceImpl implements OrderService {
    private final MarketRepository marketRepository;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(MarketRepository marketRepository,
                            OrderRepository orderRepository) {
        this.marketRepository = marketRepository;
        this.orderRepository = orderRepository;

        TimerTaskOkex timerTaskOkex =
                new TimerTaskOkex(marketRepository);
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(
                timerTaskOkex,
                0,
                24 * 60 * 60 * 1000);
    }

    @Override
    public Order createOrder(PlaceOrder placeOrder) throws IOException {
        // In You Customer Model You Should have Apikey ,SecretKey , Passphrase
        // (You Can Decide To Save Any of these fields For your business logic
        // Then Create Config By That
        ApiService apiService = new ApiServiceImpl(
                new Config(
                    "...",
                    "...",
                    "..."
                )
        );
        Market market = marketRepository.findByInstId(placeOrder.getInstId())
                .orElseThrow(() -> new RuntimeException("INVALID_MARKET"));
        if (!checkMaxMinOrder(placeOrder, market, apiService)) {
            throw new RuntimeException("Unacceptable Amount :" + placeOrder.getSize());
        }
        OkexResponse okexResponse = apiService.placeOrder(placeOrder);
        if (okexResponse.getCode() == 1) {
            throw new RuntimeException(okexResponse.getMsg());
        }
        Map dataOrder = (Map) okexResponse.getData().get(0);
        Order order = new Order(
                dataOrder.get("ordId").toString(),
                placeOrder.getUnitPrice(),
                placeOrder.getSize(),
                placeOrder.getSide(),
                placeOrder.getType());
        return orderRepository.save(order);
    }

    private boolean checkMaxMinOrder(PlaceOrder placeOrder,
                                     Market market,
                                     ApiService apiService) throws IOException {
        OkexResponse okexResponse = apiService.getTickerPrice(market.getInstId());
        List data = okexResponse.getData();
        //Because we gave the InstId Data will have only one element
        Map tickerData = (Map) data.get(0);
        Double tickerPrice = Double.valueOf(tickerData.get("last").toString());
        if (placeOrder.getSide().equals(OrderSide.SELL)) {
            if (placeOrder.getSize() < market.getMinAmount() ||
                    placeOrder.getSize() > market.getMaxAmount() ||
                    ReusableMethods.operationDouble(placeOrder.getSize(),
                            tickerPrice,
                            '*') < market.getMinNotional()) {
                return false;
            }
        } else {
            double wholePrice = ReusableMethods.
                    operationDouble(placeOrder.getUnitPrice(),
                            placeOrder.getSize(),
                            '*');
            if (wholePrice < market.getMinAmount() ||
                    wholePrice > market.getMaxAmount() ||
                    ReusableMethods.operationDouble(placeOrder.getSize(),
                            tickerPrice,
                            '*') < market.getMinNotional()) {
                return false;
            }
        }
        return true;
    }
}
