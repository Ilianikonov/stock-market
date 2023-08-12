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
public class TransactionService {
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
        List<String> currencyNameList = databaseТransactionRepository.getTotalBalance(traderId);
        for (String name: currencyNameList){
            if (!Objects.equals(currency, name)) {
                amount += webCurrencyService.convert(name, currency, getBalanceByCurrency(traderId, name).getAmount());
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
    public void currencyExchange(long traderId, double count, String currencyTo, String currencyFrom) {
        double commission = count * 0.1;
        double amountFrom = count - commission;
        double amountTo = webCurrencyService.convert(currencyTo, currencyFrom, amountFrom);

        if (getBalanceByCurrency(traderId,currencyFrom).getAmount() <  amountFrom){
            throw new ObjectNotFoundException("нет Currency для обмена");
        }
        databaseТransactionRepository.currencyExchange(traderId, currencyTo, currencyFrom, commission, amountTo, amountFrom);
    }
}
