package com.example.stockmarket.service.currency;

import lombok.Value;

import java.util.Map;

@Value
public class ConvertCurrencyResponse {
    private int status;
    private String massage;
    private Map <String, Double> data;
}
