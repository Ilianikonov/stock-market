package com.example.stockmarket.controller.response;

import lombok.Data;

@Data
public class GetBalanceResponse {
    private Double amount;
    private String currency;
}
