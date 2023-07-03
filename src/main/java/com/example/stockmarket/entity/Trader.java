package com.example.stockmarket.entity;

import lombok.Data;

import java.util.List;
@Data
public class Trader {
    private long id;
    private String name;
    private char[] password;
    private List<Balance> totalBalance;
}
