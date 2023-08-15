package com.example.stockmarket.service;

import com.example.stockmarket.dao.DatabaseТransactionRepository;
import com.example.stockmarket.entity.Balance;
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
    private final WebCurrencyService webCurrencyService;


    public void makeDepositing(long traderId, double count, String currency) {
        double commission = 0;
        databaseТransactionRepository.makeDepositing(traderId, count, currency, commission);
    }

    public void withdrawCurrency(long traderId, double count, String currency) {
        double commission = 0;
        if ((databaseТransactionRepository.getAmountOfAdditions(traderId,currency) - databaseТransactionRepository.getAmountOfSubtractions(traderId,currency)) >= count){
            databaseТransactionRepository.withdrawCurrency(traderId, count, currency, commission);
        }else {
            throw new ObjectNotFoundException("недостаточно средств для вывода");
        }
    }

    public Balance getTotalBalance(long traderId, String currency) {
        Balance balanceTotal = new Balance();
        double amount = 0.0;
        balanceTotal.setCurrencyName(currency);
        List<String> currencyNameList = databaseТransactionRepository.getAllCurrenciesOfTrader(traderId);
        for (String name: currencyNameList){
            if (!Objects.equals(currency, name)) {
                amount += getBalanceByCurrency(traderId, name).getAmount() * webCurrencyService.convert(name, currency, 1);
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
