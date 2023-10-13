package com.example.stockmarket.controller;

import com.example.stockmarket.controller.request.transaction.CurrencyExchangeRequest;
import com.example.stockmarket.controller.request.transaction.MakeDepositingRequest;
import com.example.stockmarket.controller.request.transaction.WithdrawCurrencyRequest;
import com.example.stockmarket.controller.response.TraderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/transaction")
public interface TransactionControllerl {
    @Operation(summary = "пополнить депозит", description = "Позволяет пополнить баланс валюты трейдера")
    @ApiResponse(responseCode = "200", description = "topped up my balance")
    @PostMapping("/makeDepositing")
    void makeDepositing(@RequestBody MakeDepositingRequest makeDepositingRequest);
    @Operation(summary = "вывести валюту", description = "Позволяет вывести валюту трейдера")
    @ApiResponse(responseCode = "200", description = "Withdrew currency")
    @PostMapping("/withdrawCurrency")
    void withdrawCurrency(@RequestBody WithdrawCurrencyRequest withdrawCurrencyRequest);
    @Operation(summary = "обмен валюты", description = "Позволяет обменивать существующую валюту трейдера на другие")
    @ApiResponse(responseCode = "200", description = "exchanged currency")
    @PostMapping("/currencyExchange")
    void currencyExchange(@RequestBody CurrencyExchangeRequest currencyExchangeRequest);
}

