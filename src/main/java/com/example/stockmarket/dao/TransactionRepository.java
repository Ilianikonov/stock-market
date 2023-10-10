package com.example.stockmarket.dao;

import com.example.stockmarket.entity.Transaction;

import java.util.List;

public interface TransactionRepository {
    void save(Transaction transaction);
    List<String> getAllCurrenciesOfTrader(long traderId);
}
