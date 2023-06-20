package com.example.stockmarket.service;

import com.example.stockmarket.dao.DatabaseTraderRepository;
import com.example.stockmarket.entity.Trader;
import org.springframework.stereotype.Service;

@Service
public class TraderService {
    private final DatabaseTraderRepository databaseTraderRepository;

    public TraderService(DatabaseTraderRepository databaseTraderRepository) {
        this.databaseTraderRepository = databaseTraderRepository;
    }

    public void clear() {
        databaseTraderRepository.clear();
    }


    public Trader createTrader(Trader trader) {
        return databaseTraderRepository.createTrader(trader);
    }


    public Trader updateTrader(Trader trader) {
        return databaseTraderRepository.updateTrader(trader);
    }


    public Trader deleteTraderById(long id) {
        return databaseTraderRepository.deleteTraderById(id);
    }


    public Trader getTraderById(long id) {
        return databaseTraderRepository.getTraderById(id);
    }
}
