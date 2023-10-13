package com.example.stockmarket.controller;

import com.example.stockmarket.controller.request.transaction.CurrencyExchangeRequest;
import com.example.stockmarket.controller.request.transaction.MakeDepositingRequest;
import com.example.stockmarket.controller.request.transaction.WithdrawCurrencyRequest;
import com.example.stockmarket.service.TransactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name="Транзакция", description="управление средствами трейдера")
public class TransactionController implements TransactionControllerl {
    private final TransactionService transactionService;

    public void makeDepositing(MakeDepositingRequest makeDepositingRequest){
        transactionService.makeDepositing(makeDepositingRequest.getTraderId(), makeDepositingRequest.getReceivedAmount(), makeDepositingRequest.getReceivedCurrency());
    }

    public void withdrawCurrency(WithdrawCurrencyRequest withdrawCurrencyRequest){
        transactionService.withdrawCurrency(withdrawCurrencyRequest.getTraderId(), withdrawCurrencyRequest.getGivenAmount(), withdrawCurrencyRequest.getGivenCurrency());
    }

    public void currencyExchange(CurrencyExchangeRequest currencyExchangeRequest) {
        transactionService.currencyExchange(currencyExchangeRequest.getTraderId(), currencyExchangeRequest.getGivenAmount(), currencyExchangeRequest.getGivenCurrency(), currencyExchangeRequest.getReceivedCurrency());
    }

}
