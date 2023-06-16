package com.example.stockmarket;

import com.example.stockmarket.dao.DatabasePortfolioRepository;
import com.example.stockmarket.dao.DatabaseTraderRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StockMarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockMarketApplication.class, args);
    }

}
