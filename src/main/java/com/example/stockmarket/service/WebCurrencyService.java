package com.example.stockmarket.service;

import org.springframework.stereotype.Service;

@Service
public class WebCurrencyService {
    public double getCostCurrency(String addCurrency, String reduceCurrency) {
        return 70.8; // "https://currate.ru/api/?get=rates&pairs="+ reduceCurrency + addCurrency+ "&key=8290c72cf52d76f9350c7006a6fc9da4";
    }
}