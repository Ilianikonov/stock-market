package com.example.stockmarket.controller.response;

import lombok.Data;

import java.util.Map;

@Data
public class ErrorResponse {
    private String message;
    private Map<Object, Object> data;
}
