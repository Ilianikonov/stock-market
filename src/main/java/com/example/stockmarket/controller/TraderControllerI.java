package com.example.stockmarket.controller;

import com.example.stockmarket.controller.request.trader.CreateTraderRequest;
import com.example.stockmarket.controller.request.trader.UpdateTraderRequest;
import com.example.stockmarket.controller.response.TraderResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/trader")
public interface TraderControllerI {
    @Operation(summary = "создание trader")
    @PostMapping("/createTrader")
    TraderResponse createTrader(@RequestBody CreateTraderRequest createTraderRequest);

    @PostMapping("/updateTrader")
    TraderResponse updateTrader (@RequestBody UpdateTraderRequest updateTraderRequest);

    @DeleteMapping("/deleteTraderById/{id}")
    TraderResponse deleteTraderById (@PathVariable Long id);

    @GetMapping("/getTraderById/{id}")
    ResponseEntity<TraderResponse> getTraderById(@PathVariable Long id);
}
