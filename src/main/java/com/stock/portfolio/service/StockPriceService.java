package com.stock.portfolio.service;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StockPriceService {

    private final RestTemplate restTemplate = new RestTemplate();

    public double getStockPrice(String symbol) {
        // https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=TSLA&apikey=1
        String apiUrl = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=" + symbol + "&apikey=1";

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
            // Parse JSON response to extract stock price
            JSONObject json = new JSONObject(response.getBody());
            String price = json.getJSONObject("Global Quote").getString("05. price");
            return Double.parseDouble(price);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch stock price for " + symbol);
        }
    }
}

