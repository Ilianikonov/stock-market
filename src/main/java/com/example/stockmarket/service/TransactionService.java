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


    public void currencyExchange(long traderId, double count, String givenCurrency, String receivedCurrency) {
        double commission = count * 0.1;
        double amountFrom = count - commission;
        double amountTo = webCurrencyService.convert(givenCurrency, receivedCurrency, amountFrom);

        if (databaseТransactionRepository.getAmountOfAdditions(traderId,receivedCurrency) - databaseТransactionRepository.getAmountOfSubtractions(traderId,receivedCurrency) <  amountFrom){
            throw new ObjectNotFoundException("нет Currency для обмена");
        }
        databaseТransactionRepository.currencyExchange(traderId, givenCurrency, receivedCurrency, commission, amountTo, amountFrom);
    }
}
