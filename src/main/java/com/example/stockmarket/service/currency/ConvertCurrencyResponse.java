package com.example.stockmarket.service.currency;

import lombok.Data;
import lombok.Value;

import java.util.Map;

@Data
public class ConvertCurrencyResponse {
    private int status;
    private String massage;
    private Map <String, Double> data;
}
