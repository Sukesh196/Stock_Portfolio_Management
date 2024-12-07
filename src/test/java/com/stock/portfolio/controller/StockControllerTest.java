package com.stock.portfolio.controller;

import com.stock.portfolio.model.Stock;
import com.stock.portfolio.service.StockService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StockController.class)
class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockService stockService;

    @Test
    void testRemoveStock() throws Exception {
        mockMvc.perform(delete("/api/stocks/1"))
                .andExpect(status().isOk());

        Mockito.verify(stockService, Mockito.times(1)).removeStock(1L);
    }

    @Test
    void testAddStock() throws Exception {
        Stock stock = new Stock(1L, "AAPL", 10);

        Mockito.when(stockService.addStock(Mockito.any(Stock.class))).thenReturn(stock);

        mockMvc.perform(post("/api/stocks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"symbol\":\"AAPL\", \"quantity\":10}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.symbol").value("AAPL"))
                .andExpect(jsonPath("$.quantity").value(10));
    }

    @Test
    void testGetAllStocks() throws Exception {
        List<Stock> stocks = Arrays.asList(
                new Stock(1L, "AAPL", 10),
                new Stock(2L, "TSLA", 5)
        );

        Mockito.when(stockService.getAllStocks()).thenReturn(stocks);

        mockMvc.perform(get("/api/stocks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].symbol").value("AAPL"))
                .andExpect(jsonPath("$[1].symbol").value("TSLA"));
    }


    @Test
    void testGetPortfolioValue() throws Exception {
        Mockito.when(stockService.calculateTotalValue()).thenReturn(2500.0);

        mockMvc.perform(get("/api/stocks/value"))
                .andExpect(status().isOk())
                .andExpect(content().string("2500.0"));
    }

}

