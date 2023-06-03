package com.example.stockmarket.dao;

import com.example.stockmarket.entity.Trader;

public interface TraderRepository {
    void clear();
    Trader createTrader(Trader trader);
    Trader updateTrader(Trader trader);
    Trader deleteTraderById(long id);
    void buyCurrency(Trader trader, double count, Object object);
    void saleCurrency(Trader trader, double count, Object object);
    double valuationFinancialPortfolios(Trader trader, Object object);
    double getBalance(Trader trader, Object object);
    void upBalance(Trader trader, double count, Object object);
}
