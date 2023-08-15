package com.example.stockmarket.service;

import com.example.stockmarket.dao.DatabaseТransactionRepository;
import com.example.stockmarket.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final DatabaseТransactionRepository databaseТransactionRepository;
    private final WebCurrencyService webCurrencyService;
    private final BalanceService balanceService;

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

    public void currencyExchange(long traderId, double count, String addCurrency, String reduceCurrency) {
        double commission = count * 0.1;
        double amountTo = count - commission;
        double amountFrom = (count + commission) * webCurrencyService.getCostCurrency(addCurrency, reduceCurrency);

        if (balanceService.getBalanceByCurrency(traderId,reduceCurrency).getAmount() <  amountFrom){
            throw new ObjectNotFoundException("нет Currency для обмена");
        }
        databaseТransactionRepository.currencyExchange(traderId, addCurrency, reduceCurrency, commission, amountTo, amountFrom);
    }
}
