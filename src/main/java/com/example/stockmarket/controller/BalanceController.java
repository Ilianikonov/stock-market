package com.example.stockmarket.controller;

import com.example.stockmarket.dao.DatabasePortfolioRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BalanceController {
    DatabasePortfolioRepository databasePortfolioRepository;

    public BalanceController(DatabasePortfolioRepository databasePortfolioRepository) {
        this.databasePortfolioRepository = databasePortfolioRepository;
    }
    @PostMapping("/stock-market/portfolio/buyCurrency")
    public void buyCurrency(@RequestParam long traderId,
                            @RequestParam double amount,
                            @RequestParam String currency){
        databasePortfolioRepository.buyCurrency(traderId,amount,currency);
    }
}
