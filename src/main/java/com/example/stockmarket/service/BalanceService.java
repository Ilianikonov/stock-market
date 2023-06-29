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

    public void sellCurrency(long traderId, double count, String currency) {
        databaseТransactionRepository.sellCurrency(traderId, count, currency);
    }


    public void upBalance(long traderId, double count, String currency) {
        databaseТransactionRepository.addCurrency(traderId, count, currency);
    }


    public double getTotalBalance(long traderId, String currency) {
        return databaseТransactionRepository.getTotalBalance(traderId, currency);
    }


    public double getBalanceByCurrency(long traderId, String currency) {
        return databaseТransactionRepository.getBalanceByCurrency(traderId, currency);
    }
}
