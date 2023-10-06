package com.example.stockmarket.controller.request.transactionRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class WithdrawCurrencyRequest extends TransactionRequest{
    private String givenCurrency;
    private double givenAmount;
}
