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
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/trader")
public class TraderController {
    private  final TraderService traderService;
    private final TraderConverter traderConverter;

    @PostMapping("/createTrader")
    public TraderResponse createTrader(@RequestBody CreateTraderRequest createTraderRequest){
        traderConverter.convertToTrader(createTraderRequest);
        return traderConverter.convertToTrader(traderService.createTrader(traderConverter.convertToTrader(createTraderRequest)));
    }

    @PostMapping("/updateTrader")
    public TraderResponse updateTrader (@RequestBody UpdateTraderRequest updateTraderRequest) {
        Trader trader = traderService.updateTrader(traderConverter.convertToTrader(updateTraderRequest));
        return traderConverter.convertToTrader(trader);
    }

    @DeleteMapping("/deleteTraderById/{id}")
    public TraderResponse deleteTraderById (@PathVariable Long id){
        Trader trader = traderService.deleteTraderById(id);
        return traderConverter.convertToTrader(trader);
    }

    @GetMapping("/getTraderById/{id}")
    public ResponseEntity<TraderResponse> getTraderById(@PathVariable Long id) {
        Trader trader = traderService.getTraderById(id);
        if (trader == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(traderConverter.convertToTrader(trader));
    }
}
