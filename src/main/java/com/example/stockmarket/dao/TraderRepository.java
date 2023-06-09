package com.example.stockmarket.dao;

import com.example.stockmarket.entity.Trader;

public interface TraderRepository {
    void clear();
    Trader createTrader(Trader trader);
    Trader updateTrader(Trader trader);
    Trader deleteTraderById(long id);
    Trader getTraderById(long id);
}
