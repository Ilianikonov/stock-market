package com.example.stockmarket.dao;

import com.example.stockmarket.entity.Trader;

public interface PortfolioRepository {
    void buyCurrency(long traderId, double count, String currency);
    void sellCurrency(long traderId, double count, String currency);
    void upBalance(long traderId, double count, String currency);
    double getTotalBalance(long traderId, String currency);
    double getBalanceById(long traderId, String currency);
}
