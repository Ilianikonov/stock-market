package com.ilianikonov.stockmarket.dao;

import com.ilianikonov.stockmarket.dao.mapper.TraderMapper;
import com.ilianikonov.stockmarket.entity.Trader;
import jakarta.annotation.Nullable;

import org.apache.el.parser.Node;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.ArrayList;
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
    @Transactional
    public Trader createTrader(Trader trader) {
        String sql = "INSERT INTO trader(name, password) values (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, trader.getName());
            ps.setString(2, trader.getPassword().toString());
            return ps;
        }, keyHolder);
        Number key = keyHolder.getKey();
        if (key == null){
            throw new RuntimeException ();
        }
        long traderId = key.longValue();
        List<String> roles = trader.getRoles();
        if (roles != null) {
            for (int i = 0; i < roles.size(); i++) {
                String roleName = roles.get(i);
                jdbcTemplate.update("INSERT INTO trader_to_role(trader_id, role_id) values (?,(select role.id from role where name = ?))", traderId, roleName);
            }
        }
        return getTraderById(traderId);
    }

    @Override
    @Transactional
    public Trader updateTrader(Trader trader) {
        jdbcTemplate.update("DELETE FROM  trader_to_role WHERE trader_id = ?  ", trader.getId());
        jdbcTemplate.update("UPDATE trader SET name=?, password=?, enabled=? WHERE id = ?", trader.getName(), String.valueOf(trader.getPassword()), trader.getEnabled(), trader.getId());
        List<String> roles = trader.getRoles();
        String sql = "INSERT INTO trader_to_role (role_id, trader_id) values ((select id from role where name = ?), ?)";
        List<Object[]> values = new ArrayList<>();
        if (roles != null){
            for (int i = 0; i < roles.size(); i++){
                Object[] traderToRole = {roles.get(i), trader.getId()};
                values.add(traderToRole);
            }
        }
        jdbcTemplate.batchUpdate(sql,values);
        return getTraderById(trader.getId());
    }

    @Override
    @Transactional
    public Trader deleteTraderById(long id) {
        Trader trader = getTraderById(id);
        jdbcTemplate.update("DELETE FROM  transaction WHERE trader_id = ?  ", id);
        jdbcTemplate.update("DELETE FROM  trader_to_role WHERE trader_id = ?  ", id);
        jdbcTemplate.update("DELETE FROM  trader WHERE id = ?  ", id);
        return trader;
    }

    @Override
    @Nullable
    public Trader getTraderById(long id) {
        Trader trader;
        try {
            trader =  jdbcTemplate.queryForObject("select id, name, password, creation_date, enabled from trader where trader.id = ?", new TraderMapper(), id);
            trader.setRoles(jdbcTemplate.queryForList("select role.name from trader_to_role join role on role_id = role.id where trader_id = ?", String.class, id));
        } catch (DataAccessException dataAccessException){
            return null;
        }
        return trader;
    }
}
