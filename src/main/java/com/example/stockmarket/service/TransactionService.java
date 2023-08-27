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

    public void makeDepositing(long traderId, double givenAmount, String givenCurrency) {
        double commission = 0;
        databaseТransactionRepository.makeDepositing(traderId, givenAmount, givenCurrency, commission);
    }

    public void withdrawCurrency(long traderId, double receivedAmount, String currency) {
        double commission = 0;
        if ((databaseТransactionRepository.getAmountOfAdditions(traderId, currency) - databaseТransactionRepository.getAmountOfSubtractions(traderId, currency)) >= receivedAmount) {
            databaseТransactionRepository.withdrawCurrency(traderId, receivedAmount, currency, commission);
        } else {
            throw new NotEnoughMoneyException("недостаточно средств для вывода");
        }
    }

    public void currencyExchange(long traderId, double receivedAmount, String givenCurrency, String receivedCurrency) {
        double commission = receivedAmount * 0.1;
        double givenAmount = currencyService.convert(givenCurrency, receivedCurrency, receivedAmount - commission);
        if (balanceService.getBalanceByCurrency(traderId, receivedCurrency).getAmount() < receivedAmount) {
            throw new NotEnoughMoneyException("недостаточно средств для обмена");
        }
        databaseТransactionRepository.currencyExchange(traderId, givenCurrency, receivedCurrency, commission, givenAmount, receivedAmount);
    }
}
