package com.example.stockmarket.dao;

import org.springframework.stereotype.Repository;


@Repository
public class DatabasePortfolioRepository implements PortfolioRepository{
    @Override
    public void buyCurrency(long traderId, double count, String currency) {

    }

    @Override
    public void sellCurrency(long traderId, double count, String currency) {

    }

    @Override
    public void upBalance(long traderId, double count, String currency) {

    }

    @Override
    public double getTotalBalance(long traderId, String currency) {
        return 0;
    }

    @Override
    public double getBalanceById(long traderId, String currency) {
        return 0;
    }
}
