package com.example.stockmarket.service;

import com.example.stockmarket.dao.DatabasePortfolioRepository;
import com.example.stockmarket.dao.PortfolioRepository;
import org.springframework.stereotype.Service;

@Service
public class PortfolioService implements PortfolioRepository {
    private final DatabasePortfolioRepository databasePortfolioRepository;

    public PortfolioService(DatabasePortfolioRepository databasePortfolioRepository) {
        this.databasePortfolioRepository = databasePortfolioRepository;
    }
    @Override
    public void buyCurrency(long traderId, double count, String currency) {
        databasePortfolioRepository.buyCurrency(traderId, count, currency);
    }

    @Override
    public void sellCurrency(long traderId, double count, String currency) {
        databasePortfolioRepository.sellCurrency(traderId, count, currency);
    }

    @Override
    public void upBalance(long traderId, double count, String currency) {
        databasePortfolioRepository.upBalance(traderId, count, currency);
    }

    @Override
    public double getTotalBalance(long traderId, String currency) {
        return databasePortfolioRepository.getTotalBalance(traderId, currency);
    }

    @Override
    public double getBalanceById(long traderId, String currency) {
        return databasePortfolioRepository.getBalanceById(traderId, currency);
    }
}
