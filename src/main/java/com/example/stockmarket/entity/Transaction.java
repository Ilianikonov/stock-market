package com.example.stockmarket.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Transaction {
    private long id;
    private String receivedCurrency;
    private String givenCurrency;
    private double receivedAmount;
    private double givenAmount;
    private Long traderTd;
    private TransactionType type;
    private double commission;
}
