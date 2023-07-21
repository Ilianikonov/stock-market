package com.example.stockmarket.dao;

import com.example.stockmarket.entity.Balance;

import java.util.List;

public interface PortfolioRepository {
    void buyCurrency(long traderId, double count, String currency);
    void withdrawCurrency(long traderId, double count, String currency);
    List <Balance> getTotalBalance(long traderId, String currency);
    double getBalanceByCurrency(long traderId, String currency);
    void currencyExchange(long traderId, double count, String addCurrency, String reduceCurrency);
}
