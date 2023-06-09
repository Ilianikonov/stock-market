package com.example.stockmarket.dao;

import com.example.stockmarket.entity.Trader;
import org.springframework.jdbc.core.JdbcTemplate;

public class DatabaseTraderRepository implements TraderRepository{
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    @Override
    public void clear() {
        jdbcTemplate.update("truncate trader");
    }

    @Override
    public Trader createTrader(Trader trader) {
       long traderId = jdbcTemplate.queryForObject("INSERT INTO trader(name,password)", new Object[]{trader.getName(), trader.getPassword()}, Long.class);
        return jdbcTemplate.queryForObject("SELECT id, name, password FROM player WHERE player.id = ?", new Object[]{traderId}, new TraderMapper());
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
