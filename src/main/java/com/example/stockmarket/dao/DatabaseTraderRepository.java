package com.example.stockmarket.dao;

import com.example.stockmarket.dao.mapper.TraderMapper;
import com.example.stockmarket.entity.Trader;
import jakarta.annotation.Nullable;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository
public class DatabaseTraderRepository implements TraderRepository{
    private  final JdbcTemplate jdbcTemplate;

    public DatabaseTraderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void clear() {
        jdbcTemplate.update("truncate trader");
    }

    @Override
    public Trader createTrader(Trader trader) {
        String sql = "INSERT INTO trader(name,password) values (?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, trader.getName());
            ps.setString(2, trader.getPassword().toString());
            return ps;
        }, keyHolder);
        if (keyHolder.getKey() == null){
            throw new RuntimeException ();
        }else {
            return getTraderById(keyHolder.getKey().longValue());
        }
    }

    @Override
    public Trader updateTrader(Trader trader) {
        jdbcTemplate.update("UPDATE trader SET name=?, password=? WHERE id = ?", trader.getName(), String.valueOf(trader.getPassword()), trader.getId());
        return getTraderById(trader.getId());
    }

    @Override
    public Trader deleteTraderById(long id) {
        Trader trader = getTraderById(id);
        jdbcTemplate.update("DELETE FROM  transaction WHERE trader_id = ?  ", id);
        jdbcTemplate.update("DELETE FROM  trader WHERE id = ?  ", id);
        return trader;
    }

    @Override
    @Nullable
    public Trader getTraderById(long id) {
      try {
          return jdbcTemplate.queryForObject("select id, name, password from trader where trader.id = ?", new TraderMapper(), id);
      }catch (DataAccessException dataAccessException){
          return null;
      }
    }
}
