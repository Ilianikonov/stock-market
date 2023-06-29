package com.example.stockmarket.controller;

import com.example.stockmarket.controller.request.CreateTraderRequest;
import com.example.stockmarket.entity.Trader;
import com.example.stockmarket.service.TraderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trader")
public class TraderController {
    private  final TraderService traderService;

    public TraderController(TraderService traderService) {
        this.traderService = traderService;
    }

    @PostMapping("/createTrader")
    public void createTrader(@RequestBody CreateTraderRequest createTraderRequest){
        Trader trader = new Trader(
                createTraderRequest.getId(),
                createTraderRequest.getName(),
                createTraderRequest.getPassword());
        traderService.createTrader(trader);
    }
    @PostMapping("/updateTrader")
    public Trader updateTrader (@RequestBody CreateTraderRequest createTraderRequest) {
        if (traderService.getTraderById(createTraderRequest.getId()) == null) {
            createTrader(createTraderRequest);
        }
        Trader trader = new Trader(createTraderRequest.getId(),
                createTraderRequest.getName(),
                createTraderRequest.getPassword());
        trader.setTotalBalance(createTraderRequest.getTotalBalance());
        return traderService.updateTrader(trader);
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
