package com.example.stockmarket.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class WebCurrencyServiceConfig {
    @Value("${web.currency.service.url}")
    private String url;
    @Value("${web.currency.service.key}")
    private String key;
}