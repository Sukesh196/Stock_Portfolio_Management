package com.stock.portfolio.service;

import com.stock.portfolio.model.Stock;
import com.stock.portfolio.repository.StockRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class StockServiceTest {

    @Mock
    private StockRepository stockRepository;

    @Mock
    private StockPriceService stockPriceService;

    @InjectMocks
    private StockService stockService;

    @Test
    void testAddStock() {
        Stock stock = new Stock();
        stock.setSymbol("AAPL");
        stock.setQuantity(10);

        Mockito.when(stockRepository.save(stock)).thenReturn(stock);

        Stock result = stockService.addStock(stock);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("AAPL", result.getSymbol());
        Assertions.assertEquals(10, result.getQuantity());
        Mockito.verify(stockRepository, Mockito.times(1)).save(stock);
    }

    @Test
    void testRemoveStock() {
        Long stockId = 1L;

        stockService.removeStock(stockId);

        Mockito.verify(stockRepository, Mockito.times(1)).deleteById(stockId);
    }

    @Test
    void testGetAllStocks() {
        List<Stock> stocks = Arrays.asList(
                new Stock(1L, "AAPL", 10),
                new Stock(2L, "TSLA", 5)
        );

        Mockito.when(stockRepository.findAll()).thenReturn(stocks);

        List<Stock> result = stockService.getAllStocks();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("AAPL", result.get(0).getSymbol());
        Assertions.assertEquals("TSLA", result.get(1).getSymbol());
    }

    @Test
    void testCalculateTotalValue() {
        List<Stock> stocks = Arrays.asList(
                new Stock(1L, "AAPL", 10),
                new Stock(2L, "TSLA", 5)
        );

        Mockito.when(stockRepository.findAll()).thenReturn(stocks);
        Mockito.when(stockPriceService.getStockPrice("AAPL")).thenReturn(150.0);
        Mockito.when(stockPriceService.getStockPrice("TSLA")).thenReturn(200.0);

        double totalValue = stockService.calculateTotalValue();

        Assertions.assertEquals(2500.0, totalValue);
    }
}

