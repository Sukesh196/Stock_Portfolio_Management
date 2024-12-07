package com.stock.portfolio.service;

import com.stock.portfolio.model.Stock;
import com.stock.portfolio.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockPriceService stockPriceService;

    public Stock addStock(Stock stock) {
        return stockRepository.save(stock);
    }

    public void removeStock(Long stockId) {
        stockRepository.deleteById(stockId);
    }

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public double calculateTotalValue() {
        List<Stock> stocks = stockRepository.findAll();
        return stocks.stream()
                .mapToDouble(stock -> stockPriceService.getStockPrice(stock.getSymbol()) * stock.getQuantity())
                .sum();
    }
}
