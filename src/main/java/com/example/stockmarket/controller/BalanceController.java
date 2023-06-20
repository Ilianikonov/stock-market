package com.example.stockmarket.controller;

import com.example.stockmarket.service.PortfolioService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock-market/portfolio")
public class BalanceController {
    private final PortfolioService portfolioService;

    public BalanceController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @PostMapping("/buyCurrency")
    public void buyCurrency(@RequestParam long traderId,
                            @RequestParam double amount,
                            @RequestParam String currency){
        portfolioService.buyCurrency(traderId,amount,currency);
    }
}
