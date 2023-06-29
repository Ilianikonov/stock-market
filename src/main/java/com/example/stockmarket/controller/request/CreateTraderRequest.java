package com.example.stockmarket.controller.request;

import com.example.stockmarket.entity.Balance;
import lombok.Data;

import java.util.List;

@Data
public class CreateTraderRequest {
    //@NotNull
    private long id;
    //@Size(max = 20)
    private String name;
    //@Size(min = 6)
   // @Size(max = 100)
    private char[] password;
    private List<Balance> totalBalance;
}
