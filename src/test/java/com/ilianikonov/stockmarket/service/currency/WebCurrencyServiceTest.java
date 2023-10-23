package com.ilianikonov.stockmarket.service.currency;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
@SpringBootTest
@AutoConfigureMockMvc
class WebCurrencyServiceTest {
    @Autowired
    private WebCurrencyService webCurrencyService;
    @MockBean
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
        convertCurrencyResponse.setStatus(200);
        convertCurrencyResponse.setData(data);
        Mockito.when(restTemplateMock.getForObject(url, ConvertCurrencyResponse.class, params)).thenReturn(convertCurrencyResponse);
        double amount = webCurrencyService.convert("USD","RUB",1);
        Assertions.assertEquals(60.0, amount);
    }
}