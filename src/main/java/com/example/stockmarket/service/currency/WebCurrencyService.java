package com.example.stockmarket.service.currency;

import com.example.stockmarket.config.WebCurrencyServiceConfig;
import com.example.stockmarket.exception.WebCurrencyServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WebCurrencyService implements CurrencyService {
    private final RestTemplate restTemplate;
    private final WebCurrencyServiceConfig webCurrencyServiceConfig;
    public double convert(String givenCurrency, String receivedCurrency){
        String currencyPair = givenCurrency + receivedCurrency;
        Map <String, String> params = new HashMap<>();
        params.put("get", "rates");
        params.put("pairs", currencyPair);
        params.put("key", webCurrencyServiceConfig.getKey());
        String url = webCurrencyServiceConfig.getUrl() + "/api/?get={get}&pairs={pairs}&key={key}";

        ConvertCurrencyResponse convertCurrencyResponse = restTemplate.getForObject(url, ConvertCurrencyResponse.class, params);
        if (convertCurrencyResponse != null && convertCurrencyResponse.getStatus() == 200) {
            return convertCurrencyResponse.getData().get(currencyPair);
        } else {
            throw new WebCurrencyServiceException();
        }
    }
    public double convert(String givenCurrency, String receivedCurrency, double amount) {
        return amount * convert(givenCurrency, receivedCurrency);
    }
}