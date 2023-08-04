package com.example.stockmarket.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Transaction {
    private long id;
    private String currencyNameFrom;
    private String currencyNameTo;
    private double amountFrom;
    private double amountTo;
    private Trader trader;
    private TransactionType type;
    private double commission;
}
