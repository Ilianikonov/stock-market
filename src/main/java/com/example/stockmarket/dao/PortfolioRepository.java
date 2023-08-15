package com.example.stockmarket.dao;

import java.util.List;

public interface PortfolioRepository {
    void makeDepositing(long traderId, double count, String currency, double commission);
    void withdrawCurrency(long traderId, double count, String currency, double commission);
    void currencyExchange(long traderId, String addCurrency, String reduceCurrency, double commission, double amountTo,double amountFrom);
}
