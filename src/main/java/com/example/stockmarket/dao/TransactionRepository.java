package com.example.stockmarket.dao;

import java.util.List;

public interface TransactionRepository {
    void makeDepositing(long traderId, double count, String currency, double commission);
    void withdrawCurrency(long traderId, double count, String currency, double commission);
    List<String> getAllCurrenciesOfTrader(long traderId);
    void currencyExchange(long traderId, String addCurrency, String reduceCurrency, double commission, double amountTo,double amountFrom);
}
