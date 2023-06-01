package com.example.stockmarket.entity;

public class User {
    private long id;
   private double balance;
   private String name;
   private char[] password;

    public User(long id, String name, char[] password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }
}
