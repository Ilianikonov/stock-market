package com.example.stockmarket.controller;

import com.example.stockmarket.controller.response.GetBalanceResponse;
import com.example.stockmarket.entity.Balance;
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
    @PostMapping("/addCurrency")
    public void addCurrency(@RequestParam long traderId,
                             @RequestParam double amount,
                             @RequestParam String currency){
        balanceService.addCurrency(traderId,amount,currency);
    }
    @GetMapping("/getTotalBalance")
    public double getTotalBalance(@RequestParam long traderId,
                                  @RequestParam String currency){
       return balanceService.getTotalBalance(traderId,currency);
    }
    @GetMapping("/getBalanceByCurrency")
    public GetBalanceResponse getBalanceByCurrency(@RequestParam long traderId,
                                                   @RequestParam String currency){
        GetBalanceResponse getBalanceResponse = new GetBalanceResponse();
        getBalanceResponse.setCurrency(currency);
        getBalanceResponse.setAmount(balanceService.getBalanceByCurrency(traderId,currency));
        return getBalanceResponse;
    }
}
