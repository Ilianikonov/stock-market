package com.example.stockmarket.controller;

import com.example.stockmarket.entity.Trader;
import com.example.stockmarket.service.TraderService;
import com.example.stockmarket.service.TransactionService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Hidden
@Tag(name="эксперимент", description="создан для нагрузки базы данных")
public class ExperimentController {
    private final TransactionService transactionService;
    private final TraderService traderService;

    @Operation(summary = "эксперимент добавляет 500к транзакций (пополнения депозита по 100 рублей 500 тыс раз)")
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

    @Operation(summary = "эксперимент выврда 500к транзакций (выводит валюту по 10 рублей 500 тыс раз)")
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

    @Operation(summary = "эксперимент обмена валбты 500к транзакций (обменивает 15 рублей на доллар 500 тыс раз)")
    @PostMapping("/experiment3")
    public void experiment3 (){
        Trader trader = new Trader();
        trader.setName("TestTrader 3");
        trader.setPassword("12345".toCharArray());
        Trader createdTrader = traderService.createTrader(trader);
        transactionService.makeDepositing(createdTrader.getId(),1000000000,"RUB");
        for (int i = 1; i <= 500000; i++){
            transactionService.currencyExchange(createdTrader.getId(), 15,"RUB","USD");
        }
    }
}
