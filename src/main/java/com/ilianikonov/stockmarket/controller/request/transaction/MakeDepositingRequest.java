package com.ilianikonov.stockmarket.controller.request.transaction;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MakeDepositingRequest extends TransactionRequest {
    private String receivedCurrency;
    private double receivedAmount;
}
