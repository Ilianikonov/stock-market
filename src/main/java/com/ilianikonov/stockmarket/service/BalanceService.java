package com.ilianikonov.stockmarket.service;

import com.ilianikonov.stockmarket.dao.DatabaseТransactionRepository;
import com.ilianikonov.stockmarket.entity.Balance;
import com.ilianikonov.stockmarket.service.currency.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BalanceService {
    private final DatabaseТransactionRepository databaseТransactionRepository;
    private final CurrencyService currencyService;

    public Balance getTotalBalanceByCurrency(long traderId, String currency) {
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
    public List<Balance> getTotalBalance(long traderId) {
        List<Balance> balancesTotal = new ArrayList<>();
        List<String> balanceCurrency = databaseТransactionRepository.getAllCurrenciesOfTrader(traderId);
        for (String name: balanceCurrency){
            balancesTotal.add(getBalanceByCurrency(traderId,name));
        }
        return balancesTotal;
    }

    public Balance getBalanceByCurrency(long traderId, String currency) {
        Balance balance = new Balance();
        balance.setCurrencyName(currency);
        balance.setAmount(databaseТransactionRepository.getAmountOfAdditions(traderId,currency) - databaseТransactionRepository.getAmountOfSubtractions(traderId,currency));
        return balance;
    }
}
