package com.example.stockmarket.controller.converter;

import com.example.stockmarket.controller.response.GetBalanceResponse;
import com.example.stockmarket.entity.Balance;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BalanceConverter {
    public GetBalanceResponse balanceToGetBalanceResponse(Balance balance){
        GetBalanceResponse getBalanceResponse = new GetBalanceResponse();
        getBalanceResponse.setCurrency(balance.getCurrencyName());
        getBalanceResponse.setAmount(balance.getAmount());
        return getBalanceResponse;
    }
    public List<GetBalanceResponse> balanceToGetBalanceResponse(List<Balance> balance){
        List<GetBalanceResponse> balanceResponseList = new ArrayList<>();
        for (Balance i:balance) {
            balanceResponseList.add(balanceToGetBalanceResponse(i));
        }
        return balanceResponseList;
    }
}
