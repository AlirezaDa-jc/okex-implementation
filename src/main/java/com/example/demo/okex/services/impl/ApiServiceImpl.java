package com.example.demo.okex.services.impl;

import com.example.demo.okex.module.Config;
import com.example.demo.okex.module.OkexResponse;
import com.example.demo.okex.services.ApiService;
import com.example.demo.okex.utills.DateUtils;
import com.example.demo.okex.utills.HmacSHA256Base64Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import okhttp3.*;
import okio.Buffer;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created By Alireza Dolatabadi
 * Date: 3/27/2023
 * Time: 6:46 PM
 */
public class ApiServiceImpl implements ApiService {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private final Config config;
    private final OkHttpClient client = new OkHttpClient.Builder()
            .readTimeout(10000, TimeUnit.MILLISECONDS).build();
    private final String BASE_URL = "https://www.okx.com";
    Gson gson = new Gson();

    public ApiServiceImpl(Config config) {
        this.config = config;

    }

    @Override
    public OkexResponse placeOrder() throws IOException {
        Map<String, String> body = new HashMap<>();
        body.put("instId", "BTC-USDT");
        body.put("tdMode", "cash");
        body.put("tag", "cb1cecdf2c62SCDE");
        body.put("sz", "0.0001");
        body.put("px", "20000");
        body.put("side", "buy");
        body.put("ordType", "limit");

        RequestBody requestBody = RequestBody.create(createJson(body), JSON);
        final String timestamp = DateUtils.getUnixTime();
        String path = "/api/v5/trade/order";
        Request request = new Request.Builder()
                .url(BASE_URL + path)
                .post(requestBody)
                .addHeader("Content-Type", "application/json")
                .addHeader("OK-ACCESS-KEY", config.getApiKey())
                .addHeader("OK-ACCESS-SIGN", createSign(timestamp, "POST", path, bodyConvertor(requestBody), null))
                .addHeader("OK-ACCESS-TIMESTAMP", timestamp)
                .addHeader("OK-ACCESS-PASSPHRASE", config.getPassPhrase())
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        ResponseBody responseBody = response.body();
        OkexResponse okexResponse = gson.fromJson(responseBody.string(), OkexResponse.class);
        response.close();
        return okexResponse;
    }

    @Override
    public OkexResponse getOrderDetails(String ordId, String instId) throws IOException {
        final String timestamp = DateUtils.getUnixTime();
        String path = "/api/v5/trade/order";
        Request request = new Request.Builder()
                .url(BASE_URL + path + "?instId=" + instId + "&ordId=" + ordId)
                .addHeader("Content-Type", "application/json")
                .addHeader("OK-ACCESS-KEY", config.getApiKey())
                .addHeader("OK-ACCESS-SIGN", createSign(timestamp, "GET", path, null, "instId=" + instId + "&ordId=" + ordId))
                .addHeader("OK-ACCESS-TIMESTAMP", timestamp)
                .addHeader("OK-ACCESS-PASSPHRASE", config.getPassPhrase())
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        ResponseBody responseBody = response.body();
        OkexResponse okexResponse = gson.fromJson(responseBody.string(), OkexResponse.class);
        response.close();
        return okexResponse;
    }

    @Override
    public OkexResponse cancelOrder(String ordId) throws IOException {
        Map<String, String> body = new HashMap<>();
        body.put("instId", "BTC-USDT");
        body.put("ordId", ordId);

        RequestBody requestBody = RequestBody.create(createJson(body), JSON);
        final String timestamp = DateUtils.getUnixTime();
        String path = "/api/v5/trade/cancel-order";
        Request request = new Request.Builder()
                .url(BASE_URL + path)
                .post(requestBody)
                .addHeader("Content-Type", "application/json")
                .addHeader("OK-ACCESS-KEY", config.getApiKey())
                .addHeader("OK-ACCESS-SIGN", createSign(timestamp, "POST", path, bodyConvertor(requestBody), null))
                .addHeader("OK-ACCESS-TIMESTAMP", timestamp)
                .addHeader("OK-ACCESS-PASSPHRASE", config.getPassPhrase())
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        ResponseBody responseBody = response.body();
        OkexResponse okexResponse = gson.fromJson(responseBody.string(), OkexResponse.class);
        response.close();
        return okexResponse;
    }

    @Override
    public OkexResponse transferFunds() throws IOException {
        Map<String, String> body = new HashMap<>();
        body.put("ccy", "BTC");
        body.put("amt", "0.001");
        body.put("from", "6"); // 6 : Funding Account
        body.put("to", "18"); // 18 Trading Account
        // 0: transfer within account
        // 1: master account to sub-account (Only applicable to API Key from master account)
        // 2: sub-account to master account (Only applicable to API Key from master account)
        // 3: sub-account to master account (Only applicable to APIKey from sub-account)
        // 4: sub-account to sub-account (Only applicable to APIKey from sub-account, and target account needs to be another sub-account which belongs to same master account)
        // The default is 0.
        body.put("type", "0");
//        body.put("subAcct", "test"); // Name Of Sub Account (Conditional : If Type is : 1 2 or 4)

        RequestBody requestBody = RequestBody.create(createJson(body), JSON);
        final String timestamp = DateUtils.getUnixTime();
        String path = "/api/v5/asset/transfer";
        Request request = new Request.Builder()
                .url(BASE_URL + path)
                .post(requestBody)
                .addHeader("Content-Type", "application/json")
                .addHeader("OK-ACCESS-KEY", config.getApiKey())
                .addHeader("OK-ACCESS-SIGN", createSign(timestamp, "POST", path, bodyConvertor(requestBody), null))
                .addHeader("OK-ACCESS-TIMESTAMP", timestamp)
                .addHeader("OK-ACCESS-PASSPHRASE", config.getPassPhrase())
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        ResponseBody responseBody = response.body();
        OkexResponse okexResponse = gson.fromJson(responseBody.string(), OkexResponse.class);
        response.close();
        return okexResponse;
    }

    @Override
    public OkexResponse getDepositAddress() throws IOException {

        final String timestamp = DateUtils.getUnixTime();
        String path = "/api/v5/asset/deposit-address";
        Request request = new Request.Builder()
                .url(BASE_URL + path + "?ccy=BTC")
                .addHeader("Content-Type", "application/json")
                .addHeader("OK-ACCESS-KEY", config.getApiKey())
                .addHeader("OK-ACCESS-SIGN", createSign(timestamp, "GET", path, null, "ccy=BTC"))
                .addHeader("OK-ACCESS-TIMESTAMP", timestamp)
                .addHeader("OK-ACCESS-PASSPHRASE", config.getPassPhrase())
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        ResponseBody responseBody = response.body();
        OkexResponse okexResponse = gson.fromJson(responseBody.string(), OkexResponse.class);
        response.close();
        return okexResponse;
    }

    @Override
    public OkexResponse getDepositHistory() throws IOException {
        final String timestamp = DateUtils.getUnixTime();
        String path = "/api/v5/asset/deposit-history";
        Request request = new Request.Builder()
                .url(BASE_URL + path)
                .addHeader("Content-Type", "application/json")
                .addHeader("OK-ACCESS-KEY", config.getApiKey())
                .addHeader("OK-ACCESS-SIGN", createSign(timestamp, "GET", path, null, null))
                .addHeader("OK-ACCESS-TIMESTAMP", timestamp)
                .addHeader("OK-ACCESS-PASSPHRASE", config.getPassPhrase())
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        ResponseBody responseBody = response.body();
        OkexResponse okexResponse = gson.fromJson(responseBody.string(), OkexResponse.class);
        response.close();
        return okexResponse;
    }

    @Override
    public OkexResponse createWithdrawRequest() throws IOException {
        Map<String, String> body = new HashMap<>();
        body.put("ccy", "USDT");
        body.put("amt", "10");
        body.put("dest", "4");// 3 internal , 4 On chain
        body.put("toAddr", ""); // If On Chain -> formatted as 'address:tag' , if internal -> recipient address
//        body.put("areaCode", ""); // if toAddr Is a Phone Number
        body.put("fee", "");
        body.put("chain", "USDT-ERC20"); // Chain Name

        RequestBody requestBody = RequestBody.create(createJson(body), JSON);
        final String timestamp = DateUtils.getUnixTime();
        String path = "/api/v5/asset/withdrawal";
        Request request = new Request.Builder()
                .url(BASE_URL + path)
                .post(requestBody)
                .addHeader("Content-Type", "application/json")
                .addHeader("OK-ACCESS-KEY", config.getApiKey())
                .addHeader("OK-ACCESS-SIGN", createSign(timestamp, "POST", path, bodyConvertor(requestBody), null))
                .addHeader("OK-ACCESS-TIMESTAMP", timestamp)
                .addHeader("OK-ACCESS-PASSPHRASE", config.getPassPhrase())
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        ResponseBody responseBody = response.body();
        OkexResponse okexResponse = gson.fromJson(responseBody.string(), OkexResponse.class);
        response.close();
        return okexResponse;
    }

    @Override
    public OkexResponse cancelWithdrawRequest(String wdId) throws IOException {
        Map<String, String> body = new HashMap<>();
        body.put("wdId", wdId);

        RequestBody requestBody = RequestBody.create(createJson(body), JSON);
        final String timestamp = DateUtils.getUnixTime();
        String path = "/api/v5/asset/cancel-withdrawal";
        Request request = new Request.Builder()
                .url(BASE_URL + path)
                .post(requestBody)
                .addHeader("Content-Type", "application/json")
                .addHeader("OK-ACCESS-KEY", config.getApiKey())
                .addHeader("OK-ACCESS-SIGN", createSign(timestamp, "POST", path, bodyConvertor(requestBody), null))
                .addHeader("OK-ACCESS-TIMESTAMP", timestamp)
                .addHeader("OK-ACCESS-PASSPHRASE", config.getPassPhrase())
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        ResponseBody responseBody = response.body();
        OkexResponse okexResponse = gson.fromJson(responseBody.string(), OkexResponse.class);
        response.close();
        return okexResponse;
    }

    @Override
    public OkexResponse getWithdrawHistory() throws IOException {
        final String timestamp = DateUtils.getUnixTime();
        String path = "/api/v5/asset/withdrawal-history";
        Request request = new Request.Builder()
                .url(BASE_URL + path)
                .addHeader("Content-Type", "application/json")
                .addHeader("OK-ACCESS-KEY", config.getApiKey())
                .addHeader("OK-ACCESS-SIGN", createSign(timestamp, "GET", path, null, null))
                .addHeader("OK-ACCESS-TIMESTAMP", timestamp)
                .addHeader("OK-ACCESS-PASSPHRASE", config.getPassPhrase())
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        ResponseBody responseBody = response.body();
        OkexResponse okexResponse = gson.fromJson(responseBody.string(), OkexResponse.class);
        response.close();
        return okexResponse;
    }

    @Override
    public OkexResponse getTradingAccountBalance() throws IOException {
        final String timestamp = DateUtils.getUnixTime();
        String path = "/api/v5/account/balance";
//        Query For This Api Is ccy -> You can filter by e.g ?ccy=BTC. You Can Send null too
        Request request = new Request.Builder()
                .url(BASE_URL + path + "?ccy=BTC")
                .addHeader("Content-Type", "application/json")
                .addHeader("OK-ACCESS-KEY", config.getApiKey())
                .addHeader("OK-ACCESS-SIGN", createSign(timestamp, "GET", path, null, "ccy=BTC"))
                .addHeader("OK-ACCESS-TIMESTAMP", timestamp)
                .addHeader("OK-ACCESS-PASSPHRASE", config.getPassPhrase())
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        ResponseBody responseBody = response.body();
        OkexResponse okexResponse = gson.fromJson(responseBody.string(), OkexResponse.class);
        response.close();
        return okexResponse;
    }

    @Override
    public OkexResponse getFundingAccountBalance() throws IOException {
        final String timestamp = DateUtils.getUnixTime();
        String path = "/api/v5/asset/balances";
//        Query For This Api Is ccy -> You can filter by e.g ?ccy=BTC. You Can Send null too
        Request request = new Request.Builder()
                .url(BASE_URL + path + "?ccy=BTC")
                .addHeader("Content-Type", "application/json")
                .addHeader("OK-ACCESS-KEY", config.getApiKey())
                .addHeader("OK-ACCESS-SIGN", createSign(timestamp, "GET", path, null, "ccy=BTC"))
                .addHeader("OK-ACCESS-TIMESTAMP", timestamp)
                .addHeader("OK-ACCESS-PASSPHRASE", config.getPassPhrase())
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        ResponseBody responseBody = response.body();
        OkexResponse okexResponse = gson.fromJson(responseBody.string(), OkexResponse.class);
        response.close();
        return okexResponse;
    }

    @Override
    public OkexResponse createAlgoOrder() throws IOException {
        Map<String, String> body = new HashMap<>();
        //Stop Limit
        body.put("instId", "BTC-USDT");
        body.put("tdMode", "cash");
        body.put("tag", "cb1cecdf2c62SCDE");
        body.put("sz", "0.0001");
        body.put("side", "buy");
        body.put("ordType", "conditional");
        body.put("slOrdPx", "20000");
        body.put("slTriggerPx", "18000");
        body.put("slTriggerPxType", "last");
        //Stop market
//        body.put("slOrdPx", "-1");

        RequestBody requestBody = RequestBody.create(createJson(body), JSON);
        final String timestamp = DateUtils.getUnixTime();
        String path = "/api/v5/trade/order-algo";
        Request request = new Request.Builder()
                .url(BASE_URL + path)
                .post(requestBody)
                .addHeader("Content-Type", "application/json")
                .addHeader("OK-ACCESS-KEY", config.getApiKey())
                .addHeader("OK-ACCESS-SIGN", createSign(timestamp, "POST", path, bodyConvertor(requestBody), null))
                .addHeader("OK-ACCESS-TIMESTAMP", timestamp)
                .addHeader("OK-ACCESS-PASSPHRASE", config.getPassPhrase())
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        ResponseBody responseBody = response.body();
        OkexResponse okexResponse = gson.fromJson(responseBody.string(), OkexResponse.class);
        response.close();
        return okexResponse;
    }

    @Override
    public OkexResponse getAlgoOrderDetails(String algoId) throws IOException {
        final String timestamp = DateUtils.getUnixTime();
        String path = "/api/v5/trade/order-algo";
        Request request = new Request.Builder()
                .url(BASE_URL + path + "?algoId=" + algoId)
                .addHeader("Content-Type", "application/json")
                .addHeader("OK-ACCESS-KEY", config.getApiKey())
                .addHeader("OK-ACCESS-SIGN", createSign(timestamp, "GET", path, null, "algoId=" + algoId))
                .addHeader("OK-ACCESS-TIMESTAMP", timestamp)
                .addHeader("OK-ACCESS-PASSPHRASE", config.getPassPhrase())
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        ResponseBody responseBody = response.body();
        OkexResponse okexResponse = gson.fromJson(responseBody.string(), OkexResponse.class);
        response.close();
        return okexResponse;
    }

    @Override
    public OkexResponse cancelAlgoOrder(String algoId, String instId) throws IOException {
        Map<String, String> body = new HashMap<>();
        body.put("instId", "BTC-USDT");
        body.put("algoId", algoId);

        RequestBody requestBody = RequestBody.create(createJson(body), JSON);
        final String timestamp = DateUtils.getUnixTime();
        String path = "/api/v5/trade/cancel-algos";
        Request request = new Request.Builder()
                .url(BASE_URL + path)
                .post(requestBody)
                .addHeader("Content-Type", "application/json")
                .addHeader("OK-ACCESS-KEY", config.getApiKey())
                .addHeader("OK-ACCESS-SIGN", createSign(timestamp, "POST", path, bodyConvertor(requestBody), null))
                .addHeader("OK-ACCESS-TIMESTAMP", timestamp)
                .addHeader("OK-ACCESS-PASSPHRASE", config.getPassPhrase())
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        ResponseBody responseBody = response.body();
        OkexResponse okexResponse = gson.fromJson(responseBody.string(), OkexResponse.class);
        response.close();
        return okexResponse;
    }

    @Override
    public OkexResponse getPendingAlgoOrders(String ordType) throws IOException {
        final String timestamp = DateUtils.getUnixTime();
        String path = "/api/v5/trade/orders-algo-pending";
        Request request = new Request.Builder()
                .url(BASE_URL + path + "?ordType=" + ordType)
                .addHeader("Content-Type", "application/json")
                .addHeader("OK-ACCESS-KEY", config.getApiKey())
                .addHeader("OK-ACCESS-SIGN", createSign(timestamp, "GET", path, null, "ordType=" + ordType))
                .addHeader("OK-ACCESS-TIMESTAMP", timestamp)
                .addHeader("OK-ACCESS-PASSPHRASE", config.getPassPhrase())
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        ResponseBody responseBody = response.body();
        OkexResponse okexResponse = gson.fromJson(responseBody.string(), OkexResponse.class);
        response.close();
        return okexResponse;
    }

    @Override
    public OkexResponse getHistoryAlgoOrders(String ordType, String state) throws IOException {
        final String timestamp = DateUtils.getUnixTime();
        String path = "/api/v5/trade/orders-algo-history";
        Request request = new Request.Builder()
                .url(BASE_URL + path + "?ordType=" + ordType + "&state=" + state)
                .addHeader("Content-Type", "application/json")
                .addHeader("OK-ACCESS-KEY", config.getApiKey())
                .addHeader("OK-ACCESS-SIGN", createSign(timestamp, "GET", path, null, "ordType=" + ordType + "&state=" + state))
                .addHeader("OK-ACCESS-TIMESTAMP", timestamp)
                .addHeader("OK-ACCESS-PASSPHRASE", config.getPassPhrase())
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        ResponseBody responseBody = response.body();
        OkexResponse okexResponse = gson.fromJson(responseBody.string(), OkexResponse.class);
        response.close();
        return okexResponse;
    }

    @Override
    public OkexResponse getPendingOrders() throws IOException {
        final String timestamp = DateUtils.getUnixTime();
        String path = "/api/v5/trade/orders-pending";
        Request request = new Request.Builder()
                .url(BASE_URL + path)
                .addHeader("Content-Type", "application/json")
                .addHeader("OK-ACCESS-KEY", config.getApiKey())
                .addHeader("OK-ACCESS-SIGN", createSign(timestamp, "GET", path, null, null))
                .addHeader("OK-ACCESS-TIMESTAMP", timestamp)
                .addHeader("OK-ACCESS-PASSPHRASE", config.getPassPhrase())
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        ResponseBody responseBody = response.body();
        OkexResponse okexResponse = gson.fromJson(responseBody.string(), OkexResponse.class);
        response.close();
        return okexResponse;
    }

    @Override
    public OkexResponse getOrdersHistoryLast7Days(String instType) throws IOException {
        final String timestamp = DateUtils.getUnixTime();
        String path = "/api/v5/trade/orders-history";
        Request request = new Request.Builder()
                .url(BASE_URL + path + "?instType=" + instType)
                .addHeader("Content-Type", "application/json")
                .addHeader("OK-ACCESS-KEY", config.getApiKey())
                .addHeader("OK-ACCESS-SIGN", createSign(timestamp, "GET", path, null, "instType=" + instType))
                .addHeader("OK-ACCESS-TIMESTAMP", timestamp)
                .addHeader("OK-ACCESS-PASSPHRASE", config.getPassPhrase())
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        ResponseBody responseBody = response.body();
        OkexResponse okexResponse = gson.fromJson(responseBody.string(), OkexResponse.class);
        response.close();
        return okexResponse;
    }

    @Override
    public OkexResponse getOrdersHistoryLast3Months(String instType) throws IOException {
        final String timestamp = DateUtils.getUnixTime();
        String path = "/api/v5/trade/orders-history-archive";
        Request request = new Request.Builder()
                .url(BASE_URL + path + "?instType=" + instType)
                .addHeader("Content-Type", "application/json")
                .addHeader("OK-ACCESS-KEY", config.getApiKey())
                .addHeader("OK-ACCESS-SIGN", createSign(timestamp, "GET", path, null, "instType=" + instType))
                .addHeader("OK-ACCESS-TIMESTAMP", timestamp)
                .addHeader("OK-ACCESS-PASSPHRASE", config.getPassPhrase())
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        ResponseBody responseBody = response.body();
        OkexResponse okexResponse = gson.fromJson(responseBody.string(), OkexResponse.class);
        response.close();
        return okexResponse;
    }

    @Override
    public OkexResponse getInstrument(String instType) throws IOException {
        final String timestamp = DateUtils.getUnixTime();
        String path = "/api/v5/public/instruments";
        Request request = new Request.Builder()
                .url(BASE_URL + path + "?instType=" + instType)
                .addHeader("Content-Type", "application/json")
                .addHeader("OK-ACCESS-KEY", config.getApiKey())
                .addHeader("OK-ACCESS-SIGN", createSign(timestamp, "GET", path, null, "instType=" + instType))
                .addHeader("OK-ACCESS-TIMESTAMP", timestamp)
                .addHeader("OK-ACCESS-PASSPHRASE", config.getPassPhrase())
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        ResponseBody responseBody = response.body();
        OkexResponse okexResponse = gson.fromJson(responseBody.string(), OkexResponse.class);
        response.close();
        return okexResponse;
    }

    @Override
    public OkexResponse getCurrencies() throws IOException {
        final String timestamp = DateUtils.getUnixTime();
        String path = "/api/v5/asset/currencies";
        Request request = new Request.Builder()
                .url(BASE_URL + path)
                .addHeader("Content-Type", "application/json")
                .addHeader("OK-ACCESS-KEY", config.getApiKey())
                .addHeader("OK-ACCESS-SIGN", createSign(timestamp, "GET", path, null, null))
                .addHeader("OK-ACCESS-TIMESTAMP", timestamp)
                .addHeader("OK-ACCESS-PASSPHRASE", config.getPassPhrase())
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        ResponseBody responseBody = response.body();
        OkexResponse okexResponse = gson.fromJson(responseBody.string(), OkexResponse.class);
        response.close();
        return okexResponse;
    }

    @Override
    public OkexResponse getMarketCandles(String instId) throws IOException {
        final String timestamp = DateUtils.getUnixTime();
        String path = "/api/v5/market/candles";
        Request request = new Request.Builder()
                .url(BASE_URL + path + "?instId=" + instId)
                .addHeader("Content-Type", "application/json")
                .addHeader("OK-ACCESS-KEY", config.getApiKey())
                .addHeader("OK-ACCESS-SIGN", createSign(timestamp, "GET", path, null, "instId=" + instId))
                .addHeader("OK-ACCESS-TIMESTAMP", timestamp)
                .addHeader("OK-ACCESS-PASSPHRASE", config.getPassPhrase())
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        ResponseBody responseBody = response.body();
        OkexResponse okexResponse = gson.fromJson(responseBody.string(), OkexResponse.class);
        response.close();
        return okexResponse;
    }

    private String createJson(Map<String, String> body) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(body);
    }

    private String bodyConvertor(RequestBody requestBody) throws IOException {
        String body = "";
        if (requestBody != null) {
            final Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            body = buffer.readString(StandardCharsets.UTF_8);
        }
        return body;
    }

    private String createSign(String timestamp, String method, String path, String requestBody, String query) {
        String hash = "";
        try {
            hash = HmacSHA256Base64Utils.sign(timestamp, method, path,
                    query, requestBody, config.getSecretKey());
        } catch (final Exception e) {
            System.out.println(DateFormatUtils.format(new Date(), DateUtils.TIME_STYLE_S4) + "Error HmacSHA256 ===========" + e.getMessage());
        }
        return hash;
    }
}
