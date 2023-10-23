package com.ilianikonov.stockmarket.exception;

public class NotEnoughMoneyException extends RuntimeException{
    public NotEnoughMoneyException (String message) {
        super(message);
    }
}
