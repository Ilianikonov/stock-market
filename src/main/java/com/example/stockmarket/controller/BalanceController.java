package com.example.stockmarket.controller;

import com.example.stockmarket.service.BalanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/balance")
@Slf4j
public class BalanceController {
    private final BalanceService balanceService;

    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
        System.out.println("бин поднят");
    }

    @PostMapping("/buyCurrency")
    public void buyCurrency(@RequestParam long traderId,
                            @RequestParam double amount,
                            @RequestParam String currency){
        balanceService.buyCurrency(traderId,amount,currency);
    }
    @PostMapping("/sellCurrency")
    public void sellCurrency(@RequestParam long traderId,
                            @RequestParam double amount,
                            @RequestParam String currency){
        balanceService.sellCurrency(traderId,amount,currency);
    }
    @PostMapping("/upBalance")
    public void upBalance(@RequestParam long traderId,
                             @RequestParam double amount,
                             @RequestParam String currency){
        balanceService.upBalance(traderId,amount,currency);
    }
    @GetMapping("/getTotalBalance")
    public void getTotalBalance(@RequestParam long traderId,
                          @RequestParam String currency){
        balanceService.getTotalBalance(traderId,currency);
    }
    @GetMapping("/getBalanceById")
    public void getBalanceById(@RequestParam long traderId,
                                @RequestParam String currency){
        balanceService.getBalanceById(traderId,currency);
    }
}
