package com.example.stockmarket.service.currency;

import com.example.stockmarket.exception.ObjectNotFoundException;
import com.example.stockmarket.exception.WebCurrencyServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class WebCurrencyService {
    private final RestTemplate restTemplate;
    public double convert(String receivedCurrency, String givenCurrency){
        String currencyPair = receivedCurrency + givenCurrency;
        String url = "https://currate.ru/api/?get=rates&pairs="+ currencyPair + "&key=8290c72cf52d76f9350c7006a6fc9da4";
        ConvertCurrencyResponse convertCurrencyResponse = restTemplate.getForObject(url,ConvertCurrencyResponse.class);
        if (convertCurrencyResponse != null && convertCurrencyResponse.getStatus() == 200) {
            return convertCurrencyResponse.getData().get(currencyPair);
        } else {
            throw new WebCurrencyServiceException();
        }
    }
    public double convert(String receivedCurrency, String givenCurrency, double amount) {
        return amount * convert(receivedCurrency, givenCurrency);
    }
}