package com.example.stockmarket.dao;

import com.example.stockmarket.entity.Trader;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TraderMapper implements RowMapper<Trader> {

    @Override
    public Trader mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Trader trader = new Trader(resultSet.getLong("id"),resultSet.getString("name"),resultSet.getString("password").toCharArray());
        return trader;
    }
}
