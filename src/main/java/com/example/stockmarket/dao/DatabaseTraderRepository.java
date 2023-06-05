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
        return null;
    }

    @Override
    public Trader updateTrader(Trader trader) {
        return null;
    }

    @Override
    public Trader deleteTraderById(long id) {
        return null;
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
