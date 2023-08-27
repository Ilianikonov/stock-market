package com.example.stockmarket.service;

import com.example.stockmarket.dao.DatabaseТransactionRepository;
import com.example.stockmarket.entity.Balance;
import com.example.stockmarket.service.currency.CurrencyService;
import com.example.stockmarket.exception.NotEnoughMoneyException;
import com.example.stockmarket.exception.ObjectNotFoundException;
import com.example.stockmarket.service.currency.WebCurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BalanceService {
    private final DatabaseТransactionRepository databaseТransactionRepository;
    private final CurrencyService currencyService;

    public Balance getTotalBalance(long traderId, String currency) {
        Balance balanceTotal = new Balance();
        double amount = 0.0;
        balanceTotal.setCurrencyName(currency);
        List<String> currencyNameList = databaseТransactionRepository.getAllCurrenciesOfTrader(traderId);
        for (String name: currencyNameList){
            if (!Objects.equals(currency, name)) {
                amount += getBalanceByCurrency(traderId, name).getAmount() * currencyService.convert(name, currency, 1);
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
