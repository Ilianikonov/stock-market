package com.ilianikonov.stockmarket.controller.response;

import lombok.Data;

@Data
public class GetBalanceResponse {
    private double amount;
    private String currency;
}
