package com.example.stockmarket.controller.request.transactionRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MakeDepositingRequest extends TransactionRequest {
    private String receivedCurrency;
    private double receivedAmount;
}
