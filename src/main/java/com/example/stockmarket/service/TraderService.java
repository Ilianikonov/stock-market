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
    public void buyCurrency(Trader trader, double count, Object object){
        traderRepository.buyCurrency(trader, count, object);
    }
    public  void saleCurrency(Trader trader, double count, Object object){

    }
    public double valuationFinancialPortfolios(Trader trader, Object object){

    }
    public double getBalance(Trader trader, Object object){

    }
    public void upBalance(Trader trader, double count, Object object){

    }
}
