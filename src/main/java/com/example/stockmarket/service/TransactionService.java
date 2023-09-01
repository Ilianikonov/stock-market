package com.example.stockmarket.service;

import com.example.stockmarket.dao.DatabaseТransactionRepository;
import com.example.stockmarket.exception.NotEnoughMoneyException;
import com.example.stockmarket.service.currency.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final DatabaseТransactionRepository databaseТransactionRepository;
    private final CurrencyService currencyService;
    private final BalanceService balanceService;

    public void makeDepositing(long traderId, double receivedAmount, String receivedCurrency) {
        double commission = 0;
        databaseТransactionRepository.makeDepositing(traderId, receivedAmount, receivedCurrency, commission);
    }

    public void withdrawCurrency(long traderId, double givenAmount, String givenCurrency) {
        double commission = 0;
        if ((databaseТransactionRepository.getAmountOfAdditions(traderId, givenCurrency) - databaseТransactionRepository.getAmountOfSubtractions(traderId, givenCurrency)) >= givenAmount) {
            databaseТransactionRepository.withdrawCurrency(traderId, givenAmount, givenCurrency, commission);
        } else {
            throw new NotEnoughMoneyException("недостаточно средств для вывода");
        }
    }

    public void currencyExchange(long traderId, double givenAmount, String givenCurrency, String receivedCurrency) {
        double commission = givenAmount * 0.1;
        double receivedAmount = currencyService.convert(givenCurrency, receivedCurrency, givenAmount - commission);
        if (balanceService.getBalanceByCurrency(traderId, givenCurrency).getAmount() + commission <= givenAmount) {
            throw new NotEnoughMoneyException("недостаточно средств для обмена");
        }
        databaseТransactionRepository.currencyExchange(traderId, givenCurrency, receivedCurrency, commission, receivedAmount, givenAmount);
    }
}
