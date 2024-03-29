package com.ilianikonov.stockmarket.service.currency;

public interface CurrencyService {
    double convert(String receivedCurrency, String givenCurrency);
    double convert(String receivedCurrency, String givenCurrency, double amount);
}
