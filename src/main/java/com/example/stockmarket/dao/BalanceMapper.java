package com.example.stockmarket.dao;

import com.example.stockmarket.entity.Balance;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BalanceMapper implements RowMapper<Balance> {
    @Override
    public Balance mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Balance balance = new Balance ();
        balance.setCurrencyName((resultSet.getString("currency_name")));
        balance.setAmount(resultSet.getDouble("amount"));
        return balance;
    }
}
