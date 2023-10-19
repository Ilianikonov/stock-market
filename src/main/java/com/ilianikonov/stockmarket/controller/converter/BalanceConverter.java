package com.ilianikonov.stockmarket.controller.converter;

import com.ilianikonov.stockmarket.controller.response.GetBalanceResponse;
import com.ilianikonov.stockmarket.entity.Balance;
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
