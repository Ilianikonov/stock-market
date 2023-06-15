package com.example.stockmarket.dao;

import com.example.stockmarket.dao.mapper.BalanceMapper;
import com.example.stockmarket.entity.Balance;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class DatabasePortfolioRepository implements PortfolioRepository{
    private final JdbcTemplate jdbcTemplate;

    public DatabasePortfolioRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void buyCurrency(long traderId, double count, String currency) {
        Balance balance = jdbcTemplate.queryForObject("select amount,currency_name from balance trader_id = ? and currency_name = ?", new Object[]{traderId, currency}, new BalanceMapper());
        if (balance.getCurrencyName() != currency) {
            jdbcTemplate.update("INSERT INTO balance(currency_name,amount,trader_id) values (?,?,?)", new Object[]{currency, count, traderId});
        }
        jdbcTemplate.update("UPDATE balance SET amount = ? WHERE trader_id = ? and currency_name = ?", new Object[]{count, traderId, currency});
    }

    @Override
    public void sellCurrency(long traderId, double count, String currency) {
        Balance balance = jdbcTemplate.queryForObject("select amount,currency_name from balance trader_id = ? and currency_name = ?", new Object[]{traderId, currency}, new BalanceMapper());
        if (balance.getCurrencyName() == currency && balance.getAmount() >= count) {
            jdbcTemplate.update("UPDATE balance SET amount = ? WHERE trader_id = ? and currency_name = ?", new Object[]{count, traderId, currency});
        }
    }

    @Override
    public void upBalance(long traderId, double count, String currency) {
        Balance balance = jdbcTemplate.queryForObject("select amount,currency_name from balance trader_id = ? and currency_name = ?", new Object[]{traderId, currency}, new BalanceMapper());
        if (balance.getCurrencyName() != currency) {
            jdbcTemplate.update("INSERT INTO balance(currency_name,amount,trader_id) values (?,?,?)", new Object[]{currency, count, traderId});
        }
        jdbcTemplate.update("UPDATE balance SET amount = ? WHERE trader_id = ? and currency_name = ?", new Object[]{count, traderId, currency});

    }

    @Override
    public double getTotalBalance(long traderId, String currency) {
        List <Balance> balances = jdbcTemplate.query("select amount,currency_name from balance trader_id = ? ", new Object[]{traderId}, new BalanceMapper());
        double totalBalance = 0.0;
        for (Balance balance:balances) {
        totalBalance += (balance.getAmount() * getCostCurrency(currency,balance.getCurrencyName()));
        }
        return totalBalance;
    }

    @Override
    public double getBalanceById(long traderId, String currency) {
        return 0;
    }
    public double getCostCurrency(String currency, String currency2){
       return  "https://currate.ru/api/?get=rates&pairs="+ currency + currency2+ "&key=8290c72cf52d76f9350c7006a6fc9da4";
    }
}
