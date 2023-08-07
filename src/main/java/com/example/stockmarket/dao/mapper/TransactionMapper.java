package com.example.stockmarket.dao.mapper;

import com.example.stockmarket.entity.Trader;
import com.example.stockmarket.entity.Transaction;
import com.example.stockmarket.entity.TransactionType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionMapper implements RowMapper<Transaction> {
    private final TraderMapper traderMapper = new TraderMapper();
    @Override
    public Transaction mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Transaction()
                .setId( resultSet.getLong("id"))
                .setAmountTo(resultSet.getDouble("amount_to"))
                .setAmountFrom(resultSet.getDouble("amount_from"))
                .setCommission(resultSet.getDouble("commission"))
                .setCurrencyNameTo(resultSet.getString("currency_name_to"))
                .setCurrencyNameFrom(resultSet.getString("currency_name_from"))
                .setTrader(traderMapper.mapRow(resultSet, rowNum))
                .setType(TransactionType.valueOf(resultSet.getString("transaction_type.name")));
    }
}
