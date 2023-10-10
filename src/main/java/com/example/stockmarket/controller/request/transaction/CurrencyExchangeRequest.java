package com.example.stockmarket.controller.request.transaction;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CurrencyExchangeRequest extends TransactionRequest{
    private String receivedCurrency;
    private double givenAmount;
    private String givenCurrency;
}

