package com.ilianikonov.stockmarket.dao.mapper;

import com.ilianikonov.stockmarket.entity.Transaction;
import com.ilianikonov.stockmarket.entity.TransactionType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionMapper implements RowMapper<Transaction> {
    private final TraderMapper traderMapper = new TraderMapper();
    @Override
    public Transaction mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Transaction()
                .setId( resultSet.getLong("id"))
                .setReceivedAmount(resultSet.getDouble("amount_to"))
                .setGivenAmount(resultSet.getDouble("amount_from"))
                .setCommission(resultSet.getDouble("commission"))
                .setReceivedCurrency(resultSet.getString("currency_name_to"))
                .setGivenCurrency(resultSet.getString("currency_name_from"))
                .setTraderTd(resultSet.getLong("trader_id"))
                .setType(TransactionType.valueOf(resultSet.getString("transaction_type.name")));
    }
}
