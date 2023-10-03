package com.example.stockmarket.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class Trader {
    private long id;
    private String name;
    private char[] password;

    private Date creationDate;
    private Boolean enabled;
    private  List <Balance> totalBalance;

    private List <String> roles;
}
