package com.example.stockmarket.exception;

public class LoginIsOccupiedException extends RuntimeException{
    public LoginIsOccupiedException (String message) {
        super(message);
    }
}

