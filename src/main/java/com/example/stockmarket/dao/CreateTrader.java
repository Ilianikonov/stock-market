package com.example.stockmarket.dao;

import com.example.stockmarket.entity.Balance;
import lombok.Data;

import java.util.List;
@Data
public class CreateTrader {
    private final String name;
    private final char[] password;
    private List<Balance> totalBalance;
}
