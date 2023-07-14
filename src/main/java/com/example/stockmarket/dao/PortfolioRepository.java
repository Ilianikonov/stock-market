package com.example.stockmarket.dao;

public interface PortfolioRepository {
    void buyCurrency(long traderId, double count, String currency);
    void sellCurrency(long traderId, double count, String currency);
    void addCurrency(long traderId, double count, String currency);
    double getTotalBalance(long traderId, String currency);
    double getBalanceByCurrency(long traderId, String currency);
}
