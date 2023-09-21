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
import java.util.List;


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
        String sql = "INSERT INTO trader(name, password) values (?, ?)";
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
            long traderId = keyHolder.getKey().longValue();
            for (int i = 0; i < trader.getRole().size(); i++){
                String roleName = trader.getRole().get(i);
                jdbcTemplate.update("INSERT INTO trader_to_role(trader_id,role_id) values (?,(select role.id from role where name = ?))", traderId, roleName);
            }
            return getTraderById(traderId);
        }
    }

    @Override
    public Trader updateTrader(Trader trader) {
        jdbcTemplate.update("UPDATE trader SET name=?, password=?, enabled=? WHERE id = ?", trader.getName(), String.valueOf(trader.getPassword()), trader.getEnabled(), trader.getId());
        jdbcTemplate.update("update trader_to_role set role_id = (select ) where trader_id = ?",trader.getId());
        return getTraderById(trader.getId());
    }

    @Override
    public Trader deleteTraderById(long id) {
        Trader trader = getTraderById(id);
        jdbcTemplate.update("DELETE FROM  transaction WHERE trader_id = ?  ", id);
        jdbcTemplate.update("DELETE FROM  trader WHERE id = ?  ", id);
        jdbcTemplate.update("DELETE FROM  trader_to_role WHERE trader_id = ?  ", id);
        return trader;
    }

    @Override
    @Nullable
    public Trader getTraderById(long id) {
      try {
          Trader trader =  jdbcTemplate.queryForObject("select id, name, password, creation_date, enabled from trader where trader.id = ?", new TraderMapper(), id);
          trader.setRole(jdbcTemplate.queryForList("select name from role where id = (select role_id from trader_to_role where trader_id = ?)", trader.getId()));
      }catch (DataAccessException dataAccessException){
          return null;
      }
    }
}
