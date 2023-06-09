package com.example.stockmarket.dao;

import com.example.stockmarket.dao.mapper.BalanceMapper;
import com.example.stockmarket.dao.mapper.TraderMapper;
import com.example.stockmarket.entity.Trader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
       long traderId = jdbcTemplate.queryForObject("INSERT INTO trader(name,password) values (?,?) RETURNING id", new Object[]{trader.getName(), trader.getPassword()}, Long.class);
        return jdbcTemplate.queryForObject("SELECT id, name, password FROM trader WHERE trader.id = ?", new Object[]{traderId}, new TraderMapper());
    }

    @Override
    public Trader updateTrader(Trader trader) {
        jdbcTemplate.update("UPDATE trader SET name=?, password=? WHERE id = ?", new Object[]{trader.getName(),trader.getPassword(),trader.getId()});
        return getTraderById(trader.getId());
    }

    @Override
    public Trader deleteTraderById(long id) {
        Trader trader = getTraderById(id);
        jdbcTemplate.update("DELETE FROM trader WHERE id = ?  ", id);
        return trader;
    }

    @Override
    public Trader getTraderById(long id) {
      Trader trader = jdbcTemplate.queryForObject("select id, name, password from trader trader.id = ?",new TraderMapper(),id);
      if (trader != null) {
          trader.setTotalBalance(jdbcTemplate.query("select currency_name, amount from balance where trader_id = ?", new BalanceMapper(), id));
      }
      return trader;
    }
}
