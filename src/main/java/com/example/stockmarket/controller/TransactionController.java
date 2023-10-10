package com.example.stockmarket.controller;

import com.example.stockmarket.controller.request.transactionRequest.CurrencyExchangeRequest;
import com.example.stockmarket.controller.request.transactionRequest.MakeDepositingRequest;
import com.example.stockmarket.controller.request.transactionRequest.WithdrawCurrencyRequest;
import com.example.stockmarket.controller.response.ErrorResponse;
import com.example.stockmarket.entity.Transaction;
import com.example.stockmarket.exception.NotEnoughMoneyException;
import com.example.stockmarket.exception.ObjectNotFoundException;
import com.example.stockmarket.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
