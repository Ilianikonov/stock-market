package com.ilianikonov.stockmarket.dao;

import com.ilianikonov.stockmarket.entity.Transaction;
import com.ilianikonov.stockmarket.exception.ObjectNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class DatabaseТransactionRepository implements TransactionRepository {
    private final JdbcTemplate jdbcTemplate;

    public DatabaseТransactionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public double getAmountOfSubtractions(Long traderTd, String givenCurrency) {
        Double amount = jdbcTemplate.queryForObject("select sum(amount_given) from Transaction WHERE trader_id = ? and currency_name_given = ?", Double.class, traderTd, givenCurrency);
        if(amount == null){
            amount = 0.0;
        }
        return amount;
    }
    public double getAmountOfAdditions(Long traderTd, String receivedCurrency) {
        Double sumAmountTo = jdbcTemplate.queryForObject("select sum(amount_received) from Transaction WHERE trader_id = ? and currency_name_received = ?", Double.class, traderTd, receivedCurrency);
        if (sumAmountTo == null){
            throw new ObjectNotFoundException("не найдена валюта или трейдер");
        }else {
            return sumAmountTo;
        }
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
    public void save(Transaction transaction) {
        jdbcTemplate.update("INSERT INTO Transaction(trader_id,currency_name_received,currency_name_given,commission,amount_received,amount_given,type_id) values (?, ?, ?, ?, ?, ?, (select transaction_type.id from transaction_type WHERE transaction_type.name = ?))", transaction.getTraderTd(), transaction.getReceivedCurrency(), transaction.getGivenCurrency(), transaction.getCommission(), transaction.getReceivedAmount(), transaction.getGivenAmount(), transaction.getType().toString());
    }
}