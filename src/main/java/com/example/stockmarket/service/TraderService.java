package com.example.stockmarket.service;

import com.example.stockmarket.dao.DatabaseTraderRepository;
import com.example.stockmarket.dao.TraderRepository;
import com.example.stockmarket.entity.Trader;
import org.springframework.stereotype.Service;

@Service
public class TraderService implements TraderRepository {
    private final DatabaseTraderRepository databaseTraderRepository;

    public TraderService(DatabaseTraderRepository databaseTraderRepository) {
        this.databaseTraderRepository = databaseTraderRepository;
    }

    @Override
    public void clear() {
        databaseTraderRepository.clear();
    }

    @Override
    public Trader createTrader(Trader trader) {
        return databaseTraderRepository.createTrader(trader);
    }

    @Override
    public Trader updateTrader(Trader trader) {
        return databaseTraderRepository.updateTrader(trader);
    }

    @Override
    public Trader deleteTraderById(long id) {
        return databaseTraderRepository.deleteTraderById(id);
    }

    @Override
    public Trader getTraderById(long id) {
        return databaseTraderRepository.getTraderById(id);
    }
}
