package com.example.stockmarket.dao;

import com.example.stockmarket.entity.Balance;
import com.example.stockmarket.exception.ObjectNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class DatabaseТransactionRepository implements TransactionRepository {
    private final JdbcTemplate jdbcTemplate;

    public DatabaseТransactionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void withdrawCurrency(long traderId, double count, String currency, double commission) {
        jdbcTemplate.update("INSERT INTO Transaction(commission,amount_given,trader_id,currency_name_given,type_id) values (?,?,?,?,(select transaction_type.id from transaction_type WHERE transaction_type.name = 'WITHDRAWAL'))", commission,count, traderId, currency);
    }
    public double getAmountOfSubtractions(long traderId, String currency) {
        Double amount = jdbcTemplate.queryForObject("select sum(amount_given) from Transaction WHERE trader_id = ? and currency_name_given = ?", Double.class, traderId, currency);
        if(amount == null){
            amount = 0.0;
        }
        return amount;
    }
    public double getAmountOfAdditions(long traderId, String currency) {
        Double sumAmountTo = jdbcTemplate.queryForObject("select sum(amount_received) from Transaction WHERE trader_id = ? and currency_name_received = ?", Double.class, traderId, currency);
        if (sumAmountTo == null){
            throw new ObjectNotFoundException("не найдена валюта или трейдер");
        }else {
            return sumAmountTo;
        }
    }

    @Override
    public void makeDepositing(long traderId, double count, String currency, double commission) {
        jdbcTemplate.update("INSERT INTO Transaction(commission,amount_received,trader_id,currency_name_received,type_id) values (?,?,?,?,(select transaction_type.id from transaction_type WHERE transaction_type.name = 'DEPOSITING'))",commission, count, traderId, currency);
    }
    @Override
    public List <String> getAllCurrenciesOfTrader(long traderId) {
        List<String> listCurrencyByID = jdbcTemplate.queryForList("select distinct currency_name_received from Transaction WHERE trader_id = ? and currency_name_received is not null",String.class, traderId);
        if (listCurrencyByID == null){
            throw new ObjectNotFoundException("не найдена валюта или трейдер");
        }
        return listCurrencyByID;
    }
    @Override
    public void currencyExchange(long traderId, String givenCurrency, String receivedCurrency, double commission, double receivedAmount, double givenAmount) {
        jdbcTemplate.update("INSERT INTO Transaction(trader_id,currency_name_received,currency_name_given,commission,amount_received,amount_given,type_id) values (?, ?, ?, ?, ?, ?, (select transaction_type.id from transaction_type WHERE transaction_type.name = 'EXCHANGE'))", traderId, receivedCurrency,givenCurrency,commission,receivedAmount,givenAmount);
    }
}