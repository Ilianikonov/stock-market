package com.example.stockmarket.controller.response;

import com.example.stockmarket.entity.Balance;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TraderResponse {
    private long id;
    private String name;
    private List <Balance> totalBalance;
    private boolean enabled;
    private List <String> role;
    private Date creationDate;

}
