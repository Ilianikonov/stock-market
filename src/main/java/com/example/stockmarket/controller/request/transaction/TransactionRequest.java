package com.example.stockmarket.controller.request.transaction;

import lombok.Data;

@Data
public abstract class TransactionRequest {
    private long traderId;
}
