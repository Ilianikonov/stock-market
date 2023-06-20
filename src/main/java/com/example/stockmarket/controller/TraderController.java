package com.example.stockmarket.controller;

import com.example.stockmarket.service.TraderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock-market/trader")
public class TraderController {
    private  final TraderService traderService;

    public TraderController(TraderService traderService) {
        this.traderService = traderService;
    }
}
