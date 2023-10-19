package com.ilianikonov.stockmarket.service;

import com.ilianikonov.stockmarket.dao.DatabaseТransactionRepository;
import com.ilianikonov.stockmarket.entity.Transaction;
import com.ilianikonov.stockmarket.entity.TransactionType;
import com.ilianikonov.stockmarket.exception.NotEnoughMoneyException;
import com.ilianikonov.stockmarket.service.currency.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final DatabaseТransactionRepository databaseТransactionRepository;
    private final CurrencyService currencyService;
    private final BalanceService balanceService;

    public void makeDepositing(long traderId, double receivedAmount, String receivedCurrency) {
        Transaction transaction = new Transaction();
        transaction.setTraderTd(traderId);
        transaction.setReceivedAmount(receivedAmount);
        transaction.setReceivedCurrency(receivedCurrency);
        transaction.setCommission(0);
        transaction.setType(TransactionType.DEPOSITING);
        databaseТransactionRepository.save(transaction);
    }

    public void withdrawCurrency(long traderId, double givenAmount, String givenCurrency) {
        if (givenAmount <= 0){
            throw new NotEnoughMoneyException("недопустимая сумма для вывода(вывод не должен быть меньше либо равный 0)");
        }
        Transaction transaction = new Transaction();
        transaction.setTraderTd(traderId);
        transaction.setCommission(0);
        transaction.setGivenAmount(givenAmount);
        transaction.setGivenCurrency(givenCurrency);
        transaction.setType(TransactionType.WITHDRAWAL);
        if ((databaseТransactionRepository.getAmountOfAdditions(transaction.getTraderTd(), transaction.getGivenCurrency()) - databaseТransactionRepository.getAmountOfSubtractions(transaction.getTraderTd(), transaction.getGivenCurrency())) >= givenAmount) {
            databaseТransactionRepository.save(transaction);
        } else {
            throw new NotEnoughMoneyException("недостаточно средств для вывода");
        }
    }

    public void currencyExchange(long traderId, double givenAmount, String givenCurrency, String receivedCurrency) {
        Transaction transaction = new Transaction();
        transaction.setTraderTd(traderId);
        transaction.setGivenCurrency(givenCurrency);
        transaction.setGivenAmount(givenAmount);
        transaction.setReceivedCurrency(receivedCurrency);
        transaction.setCommission(givenAmount * 0.1);
        transaction.setType(TransactionType.EXCHANGE);
        if (balanceService.getBalanceByCurrency(traderId, givenCurrency).getAmount() + transaction.getCommission() <= givenAmount) {
            throw new NotEnoughMoneyException("недостаточно средств для обмена");
        }
        double receivedAmount = currencyService.convert(givenCurrency, receivedCurrency, givenAmount - transaction.getCommission());
        transaction.setReceivedAmount(receivedAmount);

        databaseТransactionRepository.save(transaction);
    }
}
