package com.example.stockmarket.dao;

import com.example.stockmarket.dao.mapper.BalanceMapper;
import com.example.stockmarket.entity.Balance;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class DatabaseBalanceRepository implements PortfolioRepository{
    private final JdbcTemplate jdbcTemplate;

    public DatabaseBalanceRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void buyCurrency(long traderId, double count, String currency) {
        Integer countOfLines = jdbcTemplate.queryForObject("select count(*) from balance WHERE trader_id = ? and currency_name = ?", Integer.class, traderId, currency);
        String sql;
        if (countOfLines == null || countOfLines == 0) {
            sql = "INSERT INTO balance(amount,trader_id,currency_name) values (?,?,?)";
        } else {
            sql = "UPDATE balance SET amount = amount + ? WHERE trader_id = ? and currency_name = ?";
        }
        jdbcTemplate.update(sql, count, traderId, currency);
    }

    @Override
    public void sellCurrency(long traderId, double count, String currency) {
        Integer countOfLines = jdbcTemplate.queryForObject("select count(*) from balance WHERE trader_id = ? and currency_name = ?", Integer.class, traderId, currency);
        if (countOfLines != null || countOfLines != 0) {
            jdbcTemplate.update("UPDATE balance SET amount = amount - ? WHERE trader_id = ? and currency_name = ?", count, traderId, currency);
        } else {
            throw new IllegalArgumentException("нет валюты для продажи");
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
//        List <Balance> balances = jdbcTemplate.query("select amount,currency_name from balance trader_id = ? ", new Object[]{traderId}, new BalanceMapper());
//        double totalBalance = 0.0;
//        for (Balance balance:balances) {
//        totalBalance += (balance.getAmount() * getCostCurrency(currency,balance.getCurrencyName()));
//        }
        return 615.5;
    }

    @Override
    public double getBalanceById(long traderId, String currency) {
        return 0;
    }
//    public double getCostCurrency(String currency, String currency2){
//       return  "https://currate.ru/api/?get=rates&pairs="+ currency + currency2+ "&key=8290c72cf52d76f9350c7006a6fc9da4";
//    }
}
