package com.example.stockmarket.controller;

import com.example.stockmarket.controller.converter.TraderConverter;
import com.example.stockmarket.controller.request.CreateTraderRequest;
import com.example.stockmarket.controller.request.UpdateTraderRequest;
import com.example.stockmarket.entity.Trader;
import com.example.stockmarket.service.TraderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/trader")
public class TraderController {
    private  final TraderService traderService;
    private final TraderConverter traderConverter;

    @PostMapping("/createTrader")
    public Trader createTrader(@RequestBody CreateTraderRequest createTraderRequest){
        traderConverter.convertToTrader(createTraderRequest);
        return traderService.createTrader(traderConverter.convertToTrader(createTraderRequest));
    }
    @PostMapping("/updateTrader")
    public Trader updateTrader (@RequestBody UpdateTraderRequest updateTraderRequest) {
        return traderService.updateTrader(traderConverter.convertToTrader(updateTraderRequest));
    }
    @DeleteMapping("/deleteTraderById/{id}")
    public Trader deleteTraderById (@PathVariable Long id){
        return traderService.deleteTraderById(id);
    }
    @GetMapping("/getTraderById/{id}")
    public Trader getTraderById(@PathVariable Long id){
        return traderService.getTraderById(id);
    }
}
