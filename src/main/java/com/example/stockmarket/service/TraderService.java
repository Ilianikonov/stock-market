package com.example.stockmarket.service;

import com.example.stockmarket.dao.DatabaseTraderRepository;
import com.example.stockmarket.entity.Trader;
import com.example.stockmarket.exception.LoginIsOccupiedException;
import com.example.stockmarket.exception.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
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
        try {
            return databaseTraderRepository.createTrader(trader);
        }catch (DataAccessException dataAccessException) {
            log.error("при создании трейдера произошла ошибка ", dataAccessException);
            throw new LoginIsOccupiedException("трейдер с именем "+ trader.getName() +" уже существует");
        }
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
