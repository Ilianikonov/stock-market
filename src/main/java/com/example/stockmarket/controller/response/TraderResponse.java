package com.example.stockmarket.controller.response;

import com.example.stockmarket.entity.Balance;
import lombok.Data;

import java.util.List;

@Data
public class TraderResponse {
    private long id;
    private String name;
    private List <Balance> totalBalance;
    private boolean enabled = true;
    private List <String> role;

}
