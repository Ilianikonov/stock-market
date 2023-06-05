package com.example.stockmarket.service;

import com.example.stockmarket.dao.TraderRepository;
import com.example.stockmarket.entity.Trader;

public class TraderService {
    private final TraderRepository traderRepository;

    public TraderService(TraderRepository traderRepository) {
        this.traderRepository = traderRepository;
    }
    public Trader createTrader(Trader trader){
       return traderRepository.createTrader(trader);
    }
    public Trader updateTrader(Trader trader){
       Trader trader1 = new Trader(trader.getId(),trader.getName(),trader.getPassword());
       trader1.setFinancialPortfolios(trader.getFinancialPortfolios());
       return traderRepository.updateTrader(trader1);
    }
    public Trader deleteTraderById(long id){
        return traderRepository.deleteTraderById(id);
    }
}
