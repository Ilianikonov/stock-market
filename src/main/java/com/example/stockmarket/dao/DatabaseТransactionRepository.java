package com.example.stockmarket.dao;

import com.example.stockmarket.dao.mapper.BalanceMapper;
import com.example.stockmarket.entity.Balance;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


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
        if (countOfLines == null || countOfLines == 0) {
            sql = "INSERT INTO Transaction(amount,trader_id,currency_name) values (?,?,?)";
        } else {
            sql = "UPDATE Transaction SET amount = amount + ? WHERE trader_id = ? and currency_name = ?";
        }
        jdbcTemplate.update(sql, count, traderId, currency);
    }

    @Override
    public void sellCurrency(long traderId, double count, String currency) {
        Integer countOfLines = jdbcTemplate.queryForObject("select count(*) from Transaction WHERE trader_id = ? and currency_name = ?", Integer.class, traderId, currency);
        if (countOfLines != null && countOfLines != 0) {
            jdbcTemplate.update("UPDATE Transaction SET amount = amount - ? WHERE trader_id = ? and currency_name = ?", count, traderId, currency);
        } else {
            throw new IllegalArgumentException("нет валюты для продажи");
        }
    }

    @Override
    public void addCurrency(long traderId, double count, String currency) {
        Balance balance = jdbcTemplate.queryForObject("select amount,currency_name from Transaction trader_id = ? and currency_name = ?", new Object[]{traderId, currency}, new BalanceMapper());
        if (balance.getCurrencyName() != currency) {
            jdbcTemplate.update("INSERT INTO Transaction(currency_name,amount,trader_id) values (?,?,?)", new Object[]{currency, count, traderId});
        }
        jdbcTemplate.update("UPDATE Transaction SET amount = ? WHERE trader_id = ? and currency_name = ?", new Object[]{count, traderId, currency});

    }

    @Override
    public double getTotalBalance(long traderId, String currency) {
//        List <Balance> balances = jdbcTemplate.query("select amount,currency_name from Transaction trader_id = ? ", new Object[]{traderId}, new BalanceMapper());
//        double totalBalance = 0.0;
//        for (Balance balance:balances) {
//        totalBalance += (balance.getAmount() * getCostCurrency(currency,balance.getCurrencyName()));
//        }
        return 615.5;
    }

    @Override
    public double getBalanceByCurrency(long traderId, String currency) {
        return jdbcTemplate.queryForObject("select sum(amount) from Transaction WHERE trader_id = ? and currency_name = ? ", Double.class, traderId, currency);
    }
//    public double getCostCurrency(String currency, String currency2){
//       return  "https://currate.ru/api/?get=rates&pairs="+ currency + currency2+ "&key=8290c72cf52d76f9350c7006a6fc9da4";
//    }
}
