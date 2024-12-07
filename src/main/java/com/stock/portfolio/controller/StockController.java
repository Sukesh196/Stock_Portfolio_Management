package com.stock.portfolio.controller;
import com.stock.portfolio.model.Stock;
import com.stock.portfolio.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    @PostMapping
    public Stock addStock(@RequestBody Stock stock) {
        return stockService.addStock(stock);
    }

    @DeleteMapping("/{id}")
    public void removeStock(@PathVariable Long id) {
        stockService.removeStock(id);
    }

    @GetMapping
    public List<Stock> getAllStocks() {
        return stockService.getAllStocks();
    }

    @GetMapping("/value")
    public double getPortfolioValue() {
        return stockService.calculateTotalValue();
    }
}
