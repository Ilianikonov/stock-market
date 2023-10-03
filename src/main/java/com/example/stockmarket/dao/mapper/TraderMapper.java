package com.example.stockmarket.dao.mapper;

import com.example.stockmarket.entity.Trader;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TraderMapper implements RowMapper<Trader> {

    @Override
    public Trader mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Trader trader = new Trader();
        trader.setId(resultSet.getLong("id"));
        trader.setName(resultSet.getString("name"));
        trader.setPassword(resultSet.getString("password").toCharArray());
        trader.setCreationDate(resultSet.getDate("creation_date"));
        trader.setEnabled(resultSet.getBoolean("enabled"));
        return trader;
    }
}
