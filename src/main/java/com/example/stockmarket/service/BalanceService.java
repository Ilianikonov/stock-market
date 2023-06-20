package com.example.stockmarket.service;

import com.example.stockmarket.dao.DatabaseBalanceRepository;
import org.springframework.stereotype.Service;

@Service
public class BalanceService {
    private final DatabaseBalanceRepository databaseBalanceRepository;

    public BalanceService(DatabaseBalanceRepository databaseBalanceRepository) {
        this.databaseBalanceRepository = databaseBalanceRepository;
    }

    public void buyCurrency(long traderId, double count, String currency) {
        databaseBalanceRepository.buyCurrency(traderId, count, currency);
    }

    public void sellCurrency(long traderId, double count, String currency) {
        databaseBalanceRepository.sellCurrency(traderId, count, currency);
    }


    public void upBalance(long traderId, double count, String currency) {
        databaseBalanceRepository.upBalance(traderId, count, currency);
    }


    public double getTotalBalance(long traderId, String currency) {
        return databaseBalanceRepository.getTotalBalance(traderId, currency);
    }


    public double getBalanceById(long traderId, String currency) {
        return databaseBalanceRepository.getBalanceById(traderId, currency);
    }
}
