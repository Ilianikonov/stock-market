package com.ilianikonov.stockmarket.dao;

import com.ilianikonov.stockmarket.entity.Transaction;

import java.util.List;

public interface TransactionRepository {
    void save(Transaction transaction);
    List<String> getAllCurrenciesOfTrader(long traderId);
}
