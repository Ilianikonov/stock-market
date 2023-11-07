package com.ilianikonov.stockmarket.dao;

import com.ilianikonov.stockmarket.entity.Trader;

public interface TraderRepository {

    void clear();
    Trader createTrader(Trader trader);
    Trader updateTrader(Trader trader);
    Trader deleteTraderById(long id);
    Trader getTraderById(long id);
    Trader getTraderByName(String name);
}
