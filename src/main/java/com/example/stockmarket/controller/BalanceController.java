package com.example.stockmarket.controller;

import com.example.stockmarket.service.BalanceService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock-market/portfolio")
public class BalanceController {
    private final BalanceService balanceService;

    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @PostMapping("/buyCurrency")
    public void buyCurrency(@RequestParam long traderId,
                            @RequestParam double amount,
                            @RequestParam String currency){
        balanceService.buyCurrency(traderId,amount,currency);
    }
}
