package com.example.stockmarket.entity;

import lombok.Data;

import java.util.List;
@Data
public class Trader {
    private final long id;
    private String name;
    private char[] password;
    private List<Balance> financialPortfolios;

    public Trader(long id, String name, char[] password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }
}
