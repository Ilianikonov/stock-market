package com.example.stockmarket.controller;

import com.example.stockmarket.controller.request.transaction.CurrencyExchangeRequest;
import com.example.stockmarket.controller.request.transaction.MakeDepositingRequest;
import com.example.stockmarket.controller.request.transaction.WithdrawCurrencyRequest;
import com.example.stockmarket.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
@Slf4j
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/makeDepositing")
    public void makeDepositing(@RequestBody MakeDepositingRequest makeDepositingRequest){
        transactionService.makeDepositing(makeDepositingRequest.getTraderId(), makeDepositingRequest.getReceivedAmount(), makeDepositingRequest.getReceivedCurrency());
    }

    @PostMapping("/withdrawCurrency")
    public void withdrawCurrency(@RequestBody WithdrawCurrencyRequest withdrawCurrencyRequest){
        transactionService.withdrawCurrency(withdrawCurrencyRequest.getTraderId(), withdrawCurrencyRequest.getGivenAmount(), withdrawCurrencyRequest.getGivenCurrency());
    }

    @PostMapping("/currencyExchange")
    public void currencyExchange(@RequestBody CurrencyExchangeRequest currencyExchangeRequest) {
        transactionService.currencyExchange(currencyExchangeRequest.getTraderId(), currencyExchangeRequest.getGivenAmount(), currencyExchangeRequest.getGivenCurrency(), currencyExchangeRequest.getReceivedCurrency());
    }
}
