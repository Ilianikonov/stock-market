package com.example.stockmarket.dao;

import java.util.List;

public interface PortfolioRepository {
    void makeDepositing(long traderId, double count, String currency);
    void withdrawCurrency(long traderId, double count, String currency);
    List <String> getTotalBalance(long traderId, String currency);
    void currencyExchange(long traderId, String addCurrency, String reduceCurrency, double commission, double amountTo,double amountFrom);
}
