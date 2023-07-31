package com.example.stockmarket.dao;

import com.example.stockmarket.entity.Balance;
import com.example.stockmarket.entity.TransactionType;
import com.example.stockmarket.exception.ObjectNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


@Repository
public class DatabaseТransactionRepository implements PortfolioRepository{
    private final JdbcTemplate jdbcTemplate;

    public DatabaseТransactionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void buyCurrency(long traderId, double count, String currency) {
        jdbcTemplate.update("INSERT INTO Transaction(amount_to,trader_id,currency_name_to,type_id) values (?,?,?,?)",count, traderId, currency,getTypeId(TransactionType.DEPOSITING));
    }
    private double getSumAmountTo(long traderId, String currency) {
        Double sumAmountTo = jdbcTemplate.queryForObject("select sum(amount_to) from Transaction WHERE trader_id = ? and currency_name_to = ?", Double.class, traderId, currency);
    if (sumAmountTo == null){
        throw new ObjectNotFoundException("не найдена валюта или трейдер");
    }else {
        return sumAmountTo;
    }
    }
    private double getSumAmountFrom(long traderId, String currency) {
        Double sumAmountFrom = jdbcTemplate.queryForObject("select sum(amount_from) from Transaction WHERE trader_id = ? and currency_name_from = ?", Double.class, traderId, currency);
        if (sumAmountFrom == null){
            throw new ObjectNotFoundException("не найдена валюта или трейдер");
        }else {
            return sumAmountFrom;
        }
    }

    @Override
    public void withdrawCurrency(long traderId, double count, String currency) {
        if ((getSumAmountTo(traderId,currency) - getSumAmountFrom(traderId,currency)) >= count){
            jdbcTemplate.update("INSERT INTO Transaction(amount_from,trader_id,currency_name_from,type_id) values (?,?,?,?)",count, traderId, currency,getTypeId(TransactionType.WITHDRAWAL));
        }else {
            throw new ObjectNotFoundException("недостаточно средств для вывода");
        }
    }

    @Override
    public List <Balance> getTotalBalance(long traderId, String currency) {
        List<Balance> balanceList = new ArrayList<Balance>();
        List <String> currencyName = jdbcTemplate.queryForStream("select currency_name_to from Transaction WHERE trader_id = ?", ResultSet::getNString, traderId).toList();
        for (String name: currencyName){
            balanceList.add(getBalanceByCurrency(traderId,currency));
        }
        return balanceList;
    }

    /**
     * получить сумму всех поступлений по валюте с вычетом комисси
     * @param traderId
     * @param currency
     * @return
     */
    public Balance getBalanceByCurrency(long traderId, String currency){
        Balance balance = new Balance();
        balance.setCurrencyName(currency);
        balance.setAmount(getSumAmountTo(traderId,currency) - getSumAmountFrom(traderId,currency));
        return balance;
    }
    @Override
    public void currencyExchange(long traderId, double count, String addCurrency, String reduceCurrency) {
        Double amountReduceCurrency = getSumAmountTo(traderId,reduceCurrency) - getSumAmountFrom(traderId,reduceCurrency);
        if (amountReduceCurrency < count){
            throw new ObjectNotFoundException("нет Currency для обмена");
        }
        double countAddCurrency = count * getCostCurrency(addCurrency, reduceCurrency);
        double commission = countAddCurrency * 0.1;
        jdbcTemplate.update("INSERT INTO Transaction(trader_id,currency_name_from,currency_name_to,commission,amount_to,amount_from,type_id) values (?, ?, ?, ?, ?, ?)", traderId, reduceCurrency,addCurrency,commission,countAddCurrency - commission,countAddCurrency,getTypeId(TransactionType.EXCHANGE));
    }
    private int getTypeId(TransactionType transactionType){
       return jdbcTemplate.queryForObject("select transaction_type.id from transaction_type WHERE transaction_type.name = ?", Integer.class, transactionType);
    }
    public double getCostCurrency(String currency, String currency2){
       return 0.0; // "https://currate.ru/api/?get=rates&pairs="+ currency + currency2+ "&key=8290c72cf52d76f9350c7006a6fc9da4";
    }
}
