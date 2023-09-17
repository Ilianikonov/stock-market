package com.example.stockmarket.controller;

import com.example.stockmarket.entity.Trader;
import com.example.stockmarket.service.TraderService;
import com.example.stockmarket.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExperimentController {
    private final TransactionService transactionService;
    private final TraderService traderService;
    @PostMapping("/experiment")
    public void experiment (){
        Trader trader = new Trader();
        trader.setName("TestTrader 1");
        trader.setPassword("1234".toCharArray());
        Trader createdTrader = traderService.createTrader(trader);
        for (int i = 1; i <= 500000; i++){
            transactionService.makeDepositing(createdTrader.getId(),100,"RUB");
        }
    }

    @PostMapping("/experiment2")
    public void experiment2 (){
        Trader trader = new Trader();
        trader.setName("TestTrader 2");
        trader.setPassword("123456".toCharArray());
        Trader createdTrader = traderService.createTrader(trader);
        transactionService.makeDepositing(createdTrader.getId(),1000000000,"RUB");
        for (int i = 1; i <= 500000; i++){
            transactionService.withdrawCurrency(createdTrader.getId(),10,"RUB");
        }
    }

    @PostMapping("/experiment3")
    public void experiment3 (){
        Trader trader = new Trader();
        trader.setName("TestTrader 3");
        trader.setPassword("12345".toCharArray());
        Trader createdTrader = traderService.createTrader(trader);
        transactionService.makeDepositing(createdTrader.getId(),1000000000,"RUB");
        for (int i = 1; i <= 500000; i++){
            transactionService.currencyExchange(createdTrader.getId(), 15,"USD","RUB");
        }
    }
}
