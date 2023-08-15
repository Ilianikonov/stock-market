package com.example.stockmarket.service;

import com.example.stockmarket.dao.DatabaseТransactionRepository;
import com.example.stockmarket.entity.Balance;
import com.example.stockmarket.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BalanceService {
    private final DatabaseТransactionRepository databaseТransactionRepository;
    private final WebCurrencyService webCurrencyService;




    public Balance getTotalBalance(long traderId, String currency) {
        Balance balanceTotal = new Balance();
        double amount = 0.0;
        balanceTotal.setCurrencyName(currency);
        List<String> currencyNameList = databaseТransactionRepository.getTotalBalance(traderId);
        for (String name: currencyNameList){
            if (!Objects.equals(currency, name)) {
                amount += getBalanceByCurrency(traderId, name).getAmount() * webCurrencyService.getCostCurrency(name, currency);
            } else {
                amount += getBalanceByCurrency(traderId, currency).getAmount();
            }
        }
        balanceTotal.setAmount(amount);
        return balanceTotal;
    }

    public Balance getBalanceByCurrency(long traderId, String currency) {
        Balance balance = new Balance();
        balance.setCurrencyName(currency);
        balance.setAmount(databaseТransactionRepository.getAmountOfAdditions(traderId,currency) - databaseТransactionRepository.getAmountOfSubtractions(traderId,currency));
        return balance;
    }
}
