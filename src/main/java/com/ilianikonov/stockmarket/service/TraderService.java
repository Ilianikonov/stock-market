package com.ilianikonov.stockmarket.service;

import com.ilianikonov.stockmarket.dao.DatabaseTraderRepository;
import com.ilianikonov.stockmarket.entity.Trader;
import com.ilianikonov.stockmarket.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TraderService {
    private final DatabaseTraderRepository databaseTraderRepository;
    private final BalanceService balanceService;

    public TraderService(DatabaseTraderRepository databaseTraderRepository, BalanceService balanceService) {
        this.databaseTraderRepository = databaseTraderRepository;
        this.balanceService = balanceService;
    }

    public void clear() {
        databaseTraderRepository.clear();
    }


    public Trader createTrader(Trader trader) {
        return databaseTraderRepository.createTrader(trader);
    }


    public Trader updateTrader(Trader trader) {
        Trader trader1 = databaseTraderRepository.updateTrader(trader);
        trader1.setTotalBalance(balanceService.getTotalBalance(trader1.getId()));
        return trader1;
    }


    public Trader deleteTraderById(long id) {
        return databaseTraderRepository.deleteTraderById(id);
    }


    public Trader getTraderById(long id) {
        Trader trader = databaseTraderRepository.getTraderById(id);
        if (trader == null){
            throw new ObjectNotFoundException("не найден трейдер");
        }
        trader.setTotalBalance(balanceService.getTotalBalance(trader.getId()));
        return trader;
    }
}
