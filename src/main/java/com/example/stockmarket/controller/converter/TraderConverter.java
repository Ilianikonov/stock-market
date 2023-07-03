package com.example.stockmarket.controller.converter;

import com.example.stockmarket.controller.request.CreateTraderRequest;
import com.example.stockmarket.controller.request.TraderRequest;
import com.example.stockmarket.controller.request.UpdateTraderRequest;
import com.example.stockmarket.entity.Trader;
import org.springframework.stereotype.Component;
@Component
public class TraderConverter {

    public Trader convertToTrader(CreateTraderRequest createTraderRequest){

        return traderRequestConvertToTrader(createTraderRequest);
    }
    public Trader convertToTrader(UpdateTraderRequest updateTraderRequest){
        Trader trader = traderRequestConvertToTrader(updateTraderRequest);
        trader.setId(updateTraderRequest.getId());
        trader.setTotalBalance(updateTraderRequest.getTotalBalance());
        return trader;
    }
    private Trader traderRequestConvertToTrader(TraderRequest traderRequest){
        Trader trader = new Trader();
        trader.setName(traderRequest.getName());
        trader.setPassword(traderRequest.getPassword());
        return trader;
    }
}
