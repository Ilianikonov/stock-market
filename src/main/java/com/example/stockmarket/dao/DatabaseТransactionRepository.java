package com.example.stockmarket.dao;

import com.example.stockmarket.dao.mapper.BalanceMapper;
import com.example.stockmarket.entity.Balance;
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
    public void buyCurrency(long traderId, double count, String currency) {
        Integer countOfLines = jdbcTemplate.queryForObject("select count(*) from Transaction WHERE trader_id = ? and currency_name = ?", Integer.class, traderId, currency);
        String sql;
        if (countOfLines != null && countOfLines != 0) {
            sql = "UPDATE Transaction SET amount = amount + ? WHERE trader_id = ? and currency_name = ?";
        } else {
            sql = "INSERT INTO Transaction(amount,trader_id,currency_name) values (?,?,?)";
        }
        jdbcTemplate.update(sql, count, traderId, currency);
    }

    @Override
    public void withdrawCurrency(long traderId, double count, String currency) {
        Integer countOfLines = jdbcTemplate.queryForObject("select count(*) from Transaction WHERE trader_id = ? and currency_name = ?", Integer.class, traderId, currency);
        if (countOfLines != null && countOfLines != 0) {
            jdbcTemplate.update("UPDATE Transaction SET amount = amount - ? WHERE trader_id = ? and currency_name = ?", count, traderId, currency);
        } else {
            throw new RuntimeException("нет валюты для продажи");
        }
    }

    @Override
    public List <Balance> getTotalBalance(long traderId, String currency) {
 //       List <Balance> balances = jdbcTemplate.query("select amount,currency_name from Transaction where trader_id = ? ", traderId, new BalanceMapper());
//        double totalBalance;
//        for (Balance balance:balances) {
//        totalBalance += (balance.getAmount() * getCostCurrency(currency,balance.getCurrencyName()));
//        }
        return null;
    }

    @Override
    public double getBalanceByCurrency(long traderId, String currency) {
        Double amount = jdbcTemplate.queryForObject("select sum(amount) from Transaction WHERE trader_id = ? and currency_name = ? ", Double.class, traderId, currency);
        if (amount == null){
           throw new RuntimeException("не найдены trader или currency!");
        }
        return amount;
    }

    @Override
    public void currencyExchange(long traderId, double count, String addCurrency, String reduceCurrency) {
        Double amountAddCurrency = jdbcTemplate.queryForObject("select count(*) from Transaction WHERE trader_id = ? and currency_name = ?", Double.class, traderId, addCurrency);
        String addSql;
        Double amountReduceCurrency = jdbcTemplate.queryForObject("select count(*) from Transaction WHERE trader_id = ? and currency_name = ?", Double.class, traderId, reduceCurrency);
        if (amountReduceCurrency == null || amountReduceCurrency < count){
            throw new RuntimeException("нет Currency для обмена");
        }
        double countAddCurrency = count * getCostCurrency(addCurrency, reduceCurrency);
        jdbcTemplate.update("UPDATE Transaction SET amount = amount - ? WHERE trader_id = ? and currency_name = ?", count, traderId, reduceCurrency);
        if (amountAddCurrency == null) {
            addSql = "INSERT INTO Transaction(amount,trader_id,currency_name) values (?,?,?)";
        } else {
            addSql = "UPDATE Transaction SET amount = amount + ? WHERE trader_id = ? and currency_name = ?";
        }
        jdbcTemplate.update(addSql, countAddCurrency, traderId, addCurrency);
    }
    public double getCostCurrency(String currency, String currency2){
       return 0.0; // "https://currate.ru/api/?get=rates&pairs="+ currency + currency2+ "&key=8290c72cf52d76f9350c7006a6fc9da4";
    }
}
