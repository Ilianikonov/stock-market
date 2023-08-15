package com.example.stockmarket.dao;

import com.example.stockmarket.exception.ObjectNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class DatabaseТransactionRepository implements PortfolioRepository{
    private final JdbcTemplate jdbcTemplate;

    public DatabaseТransactionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void makeDepositing(long traderId, double count, String currency, double commission) {
        jdbcTemplate.update("INSERT INTO Transaction(commission,amount_to,trader_id,currency_name_to,type_id) values (?,?,?,?,(select transaction_type.id from transaction_type WHERE transaction_type.name = 'DEPOSITING'))", commission,count, traderId, currency);
    }
    public double getAmountOfAdditions(long traderId, String currency) {
        Double sumAmountTo = jdbcTemplate.queryForObject("select sum(amount_to) from Transaction WHERE trader_id = ? and currency_name_to = ?", Double.class, traderId, currency);
        if (sumAmountTo == null){
            throw new ObjectNotFoundException("не найдена валюта или трейдер");
        }else {
            return sumAmountTo;
        }
    }
    public double getAmountOfSubtractions(long traderId, String currency) {
        Double amount = jdbcTemplate.queryForObject("select sum(amount_from) from Transaction WHERE trader_id = ? and currency_name_from = ?", Double.class, traderId, currency);
        if(amount == null){
            amount = 0.0;
        }
        return amount;
    }

    @Override
    public void withdrawCurrency(long traderId, double count, String currency, double commission) {
        jdbcTemplate.update("INSERT INTO Transaction(commission,amount_from,trader_id,currency_name_from,type_id) values (?,?,?,?,(select transaction_type.id from transaction_type WHERE transaction_type.name = 'WITHDRAWAL'))",commission, count, traderId, currency);
    }
    public List <String> getAllCurrenciesOfTrader(long traderId) {
       return jdbcTemplate.queryForList("select distinct currency_name_to from Transaction WHERE trader_id = ? and currency_name_to is not null",String.class, traderId);
    }
    @Override
    public void currencyExchange(long traderId, String addCurrency, String reduceCurrency, double commission, double amountTo, double amountFrom) {
        jdbcTemplate.update("INSERT INTO Transaction(trader_id,currency_name_from,currency_name_to,commission,amount_to,amount_from,type_id) values (?, ?, ?, ?, ?, ?, (select transaction_type.id from transaction_type WHERE transaction_type.name = 'EXCHANGE'))", traderId, reduceCurrency,addCurrency,commission,amountTo,amountFrom);
    }
}
