package com.example.stockmarket.controller;

import com.example.stockmarket.controller.response.ErrorResponse;
import com.example.stockmarket.controller.response.GetBalanceResponse;
import com.example.stockmarket.service.BalanceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/balance")
@Slf4j
public class BalanceController {
    private final BalanceService balanceService;

    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @PostMapping("/buyCurrency")
    public void buyCurrency(@RequestParam long traderId,
                            @RequestParam double amount,
                            @RequestParam String currency){
        balanceService.buyCurrency(traderId,amount,currency);
    }

    @PostMapping("/sellCurrency")
    public void sellCurrency(@RequestParam long traderId,
                            @RequestParam double amount,
                            @RequestParam String currency){
        balanceService.sellCurrency(traderId, amount, currency);
    }
    @PostMapping("/addCurrency")
    public void addCurrency(@RequestParam long traderId,
                             @RequestParam double amount,
                             @RequestParam String currency){
        balanceService.addCurrency(traderId, amount, currency);
    }
    @GetMapping("/getTotalBalance")
    public double getTotalBalance(@RequestParam long traderId,
                                  @RequestParam String currency){
       return balanceService.getTotalBalance(traderId, currency);
    }
    @GetMapping("/getBalanceByCurrency")
    public ResponseEntity<GetBalanceResponse> getBalanceByCurrency(@RequestParam long traderId,
                                                         @RequestParam String currency){
        GetBalanceResponse getBalanceResponse = new GetBalanceResponse();
        getBalanceResponse.setCurrency(currency);
        double amount = balanceService.getBalanceByCurrency(traderId, currency);
        getBalanceResponse.setAmount(amount);
        return new ResponseEntity<>(getBalanceResponse, HttpStatus.OK);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleException(RuntimeException runtimeException){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(runtimeException.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
