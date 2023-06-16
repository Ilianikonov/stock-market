package com.example.stockmarket.controller;

import com.example.stockmarket.dao.DatabaseTraderRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TraderController {
    DatabaseTraderRepository databaseTraderRepository;

    public TraderController(DatabaseTraderRepository databaseTraderRepository) {
        this.databaseTraderRepository = databaseTraderRepository;
    }

}
