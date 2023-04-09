package com.example.demo.tasks;

import com.example.demo.domain.Market;
import com.example.demo.okex.module.Config;
import com.example.demo.okex.module.OkexResponse;
import com.example.demo.okex.services.ApiService;
import com.example.demo.okex.services.impl.ApiServiceImpl;
import com.example.demo.repository.MarketRepository;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimerTask;

/**
 * Created By Alireza Dolatabadi
 * Date: 4/9/2023
 * Time: 7:21 PM
 */
public class TimerTaskOkex extends TimerTask {
    private final MarketRepository marketRepository;
    private final ApiService apiService;

    public TimerTaskOkex(MarketRepository marketRepository) {
        //Here We Must have Exchange's Apikey , etc
        apiService = new ApiServiceImpl(
                new Config(
                        "...",
                        "...",
                        "..."
                )
        );
        this.marketRepository = marketRepository;
    }

    @Override
    public void run() {
        try {
            List<Market> markets = marketRepository.findAll();
            OkexResponse spot = apiService.getInstrument("SPOT");
            List<Map> data = (List) spot.getData();
            data.forEach(symbolResponse -> {
                String instId = symbolResponse.get("instId").toString();
                Optional<Market> marketInDb = markets.stream()
                        .filter(market ->
                                market.getInstId().equals(instId)
                        ).findFirst();
                Market market = marketInDb.orElseGet(() -> new Market(instId));
                market.setMaxAmount(
                        Double.valueOf(symbolResponse.get("maxMktSz").toString())
                );
                market.setMinAmount(
                        Double.valueOf(symbolResponse.get("minSz").toString())
                );
                market.setMinNotional(
                        Double.valueOf(symbolResponse.get("tickSz").toString())
                );
                markets.add(market);
            });
            marketRepository.saveAll(markets);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
