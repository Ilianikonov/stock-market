package com.example.stockmarket.dao;

import com.example.stockmarket.entity.Trader;

public interface TraderRepository {
    static void clear();
    Trader createTrader(CreateTrader trader);
    Trader updateTrader(Trader trader);
    Trader deleteTraderById(long id);
    Trader getTraderById(long id);
}
