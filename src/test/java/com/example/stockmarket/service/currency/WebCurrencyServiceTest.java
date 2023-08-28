package com.example.stockmarket.service.currency;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class WebCurrencyServiceTest {
    @InjectMocks
    private WebCurrencyService webCurrencyService;
    @Mock
    private RestTemplate restTemplateMock;
    @Value("${web.currency.service.url}")
    private String url;
    @Value("${web.currency.service.key}")
    private String key;
    @Test
    void convert() {
        Map<String, String> params = new HashMap<>();
        params.put("get", "rates");
        params.put("pairs", "USDRUB");
        params.put("key", key);
        String url = this.url + "/api/?get={get}&pairs={pairs}&key={key}";
        ConvertCurrencyResponse convertCurrencyResponse = new ConvertCurrencyResponse();
        Map<String, Double> data = new HashMap<>();
        data.put("USDRUB", 60.0);
        convertCurrencyResponse.setData(data);
        Mockito.when(restTemplateMock.getForObject(Mockito.eq(url), Mockito.eq(ConvertCurrencyResponse.class), Mockito.eq(params))).thenReturn(convertCurrencyResponse);
       double amount = webCurrencyService.convert("RUB","USD",1);
        Assertions.assertEquals(amount,60);
    }
}