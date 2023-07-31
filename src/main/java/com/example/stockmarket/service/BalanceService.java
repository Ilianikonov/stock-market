package com.example.stockmarket.service;

import com.example.stockmarket.dao.DatabaseТransactionRepository;
import com.example.stockmarket.entity.Balance;
import com.example.stockmarket.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class BalanceService {
    private final DatabaseТransactionRepository databaseТransactionRepository;

    public BalanceService(DatabaseТransactionRepository databaseТransactionRepository) {
        this.databaseТransactionRepository = databaseТransactionRepository;
    }

    public void makeDepositing(long traderId, double count, String currency) {
        databaseТransactionRepository.makeDepositing(traderId, count, currency);
    }

    public void withdrawCurrency(long traderId, double count, String currency) {
        if ((databaseТransactionRepository.getAmountOfAdditions(traderId,currency) - databaseТransactionRepository.getAmountOfSubtractions(traderId,currency)) >= count){
            databaseТransactionRepository.withdrawCurrency(traderId, count, currency);
        }else {
            throw new ObjectNotFoundException("недостаточно средств для вывода");
        }
    }

    public Balance getTotalBalance(long traderId, String currency) {
        Balance balanceTotal = new Balance();
        double amount = 0.0;
        balanceTotal.setCurrencyName(currency);
        List<Balance> balanceList = new ArrayList<>();
        List<String> currencyNameList = databaseТransactionRepository.getTotalBalance(traderId, currency);
        for (String name: currencyNameList){
            balanceList.add(getBalanceByCurrency(traderId,name));
        }
        for (Balance balance: balanceList){
            if (currency != balance.getCurrencyName()) {
                amount += balance.getAmount() * databaseТransactionRepository.getCostCurrency(balance.getCurrencyName(), currency);
            } else {
                amount += balance.getAmount();
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
    public void currencyExchange(long traderId, double count, String addCurrency, String reduceCurrency) {
        double commission = count * 0.1;
        double amountTo = count - commission;
        double amountFrom = (count + commission) * databaseТransactionRepository.getCostCurrency(addCurrency, reduceCurrency);

        if (getBalanceByCurrency(traderId,reduceCurrency).getAmount() <  amountFrom){
            throw new ObjectNotFoundException("нет Currency для обмена");
        }
        databaseТransactionRepository.currencyExchange(traderId, addCurrency, reduceCurrency, commission, amountTo, amountFrom);
    }
}
