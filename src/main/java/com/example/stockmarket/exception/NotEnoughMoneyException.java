package com.example.stockmarket.exception;

public class NotEnoughMoneyException extends RuntimeException{
    public NotEnoughMoneyException (String message) {
        super(message);
    }
}
