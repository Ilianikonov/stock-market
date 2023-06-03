package com.example.stockmarket.dao;

import com.example.stockmarket.entity.Trader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TraderMapper implements RowMapper<Trader> {
    private final JdbcTemplate jdbcTemplate;
    public TraderMapper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Trader mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Trader trader = new Trader(resultSet.getLong("id"),resultSet.getString("User_name"),resultSet.getString("password").toCharArray());
        jdbcTemplate.query("select currency_name, amount from trader inner join balance on" + trader.getId() + " = trader_id", new BalanceMapper());
        return trader;
    }
}
