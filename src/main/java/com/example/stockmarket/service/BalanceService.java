package com.example.stockmarket.service;

import com.example.stockmarket.dao.DatabaseТransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class BalanceService {
    private final DatabaseТransactionRepository databaseТransactionRepository;

    public BalanceService(DatabaseТransactionRepository databaseТransactionRepository) {
        this.databaseТransactionRepository = databaseТransactionRepository;
    }

    public void buyCurrency(long traderId, double count, String currency) {
        databaseТransactionRepository.buyCurrency(traderId, count, currency);
    }

    public void withdrawCurrency(long traderId, double count, String currency) {
        databaseТransactionRepository.withdrawCurrency(traderId, count, currency);
    }

    public double getTotalBalance(long traderId, String currency) {
        databaseТransactionRepository.getTotalBalance(traderId, currency);
        return 0.0;
    }

    public Double getBalanceByCurrency(long traderId, String currency) {
        return databaseТransactionRepository.getBalanceByCurrency(traderId, currency);
    }
    public void currencyExchange(long traderId, double count, String addCurrency, String reduceCurrency) {
        databaseТransactionRepository.currencyExchange(traderId, count, addCurrency, reduceCurrency);
    }
}
