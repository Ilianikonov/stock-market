package com.example.stockmarket.controller;

import com.example.stockmarket.controller.converter.TraderConverter;
import com.example.stockmarket.controller.request.CreateTraderRequest;
import com.example.stockmarket.controller.request.UpdateTraderRequest;
import com.example.stockmarket.controller.response.TraderResponse;
import com.example.stockmarket.entity.Trader;
import com.example.stockmarket.service.TraderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TraderController implements TraderControllerI {
    private  final TraderService traderService;
    private final TraderConverter traderConverter;

    public TraderResponse createTrader(CreateTraderRequest createTraderRequest){
        Trader trader = traderService.createTrader(traderConverter.convertToTrader(createTraderRequest));
        return traderConverter.convertToTrader(trader);
    }

    public TraderResponse updateTrader (UpdateTraderRequest updateTraderRequest) {
        Trader trader = traderService.updateTrader(traderConverter.convertToTrader(updateTraderRequest));
        return traderConverter.convertToTrader(trader);
    }

    public TraderResponse deleteTraderById (Long id){
        Trader trader = traderService.deleteTraderById(id);
        return traderConverter.convertToTrader(trader);
    }

    public ResponseEntity<TraderResponse> getTraderById(Long id) {
        Trader trader = traderService.getTraderById(id);
        if (trader == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(traderConverter.convertToTrader(trader));
    }
}
