package com.example.stockmarket.controller.converter;

import com.example.stockmarket.controller.request.trader.CreateTraderRequest;
import com.example.stockmarket.controller.request.trader.TraderRequest;
import com.example.stockmarket.controller.request.trader.UpdateTraderRequest;
import com.example.stockmarket.controller.response.TraderResponse;
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
        return trader;
    }
    private Trader traderRequestConvertToTrader(TraderRequest traderRequest){
        Trader trader = new Trader();
        trader.setName(traderRequest.getName());
        trader.setPassword(traderRequest.getPassword());
        trader.setRoles(traderRequest.getRoles());
        trader.setEnabled(traderRequest.isEnabled());
        return trader;
    }
    public TraderResponse convertToTrader (Trader trader){
        TraderResponse traderResponse = new TraderResponse();
        traderResponse.setId(trader.getId());
        traderResponse.setName(trader.getName());
        traderResponse.setEnabled(trader.getEnabled());
        traderResponse.setRole(trader.getRoles());
        traderResponse.setTotalBalance(trader.getTotalBalance());
        traderResponse.setCreationDate(trader.getCreationDate());
        return traderResponse;
    }
}
