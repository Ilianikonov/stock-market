package com.ilianikonov.stockmarket.service;

import com.ilianikonov.stockmarket.dao.DatabaseTraderRepository;
import com.ilianikonov.stockmarket.entity.Trader;
import com.ilianikonov.stockmarket.exception.LoginIsOccupiedException;
import com.ilianikonov.stockmarket.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class TraderService {
    private final DatabaseTraderRepository databaseTraderRepository;
    private final BalanceService balanceService;
    private final PasswordEncoder passwordEncoder;

    public Trader createTrader(Trader trader) {
        try {
            trader.setPassword(passwordEncoder.encode(trader.getPassword()));
            return databaseTraderRepository.createTrader(trader);
        }catch (DataAccessException dataAccessException) {
            log.error("при создании трейдера произошла ошибка ", dataAccessException);
            throw new LoginIsOccupiedException("трейдер с именем "+ trader.getName() +" уже существует");
        }
    }

    public Trader updateTrader(Trader trader) {
        Trader traderForUpdate = databaseTraderRepository.updateTrader(trader);
        traderForUpdate.setPassword(passwordEncoder.encode(trader.getPassword()));
        traderForUpdate.setTotalBalance(balanceService.getTotalBalance(traderForUpdate.getId()));
        return traderForUpdate;
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
