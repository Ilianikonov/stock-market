package com.example.stockmarket.controller;

import com.example.stockmarket.controller.response.ErrorResponse;
import com.example.stockmarket.exception.ObjectNotFoundException;
import com.example.stockmarket.service.BalanceService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
@Slf4j
@RequiredArgsConstructor
public class TransactionController {
    private final BalanceService balanceService;

    @PostMapping("/makeDepositing")
    public void makeDepositing(@RequestParam long traderId,
                               @RequestParam double amount,
                               @RequestParam String currency){
        balanceService.makeDepositing(traderId,amount,currency);
    }

    @PostMapping("/withdrawCurrency")
    public void withdrawCurrency(@RequestParam long traderId,
                                 @RequestParam double amount,
                                 @RequestParam String currency){
        balanceService.withdrawCurrency(traderId, amount, currency);
    }
    @PostMapping("/currencyExchange")
    public void currencyExchange(@RequestParam long traderId,
                                 @RequestParam double count,
                                 @RequestParam String addCurrency,
                                 @RequestParam String reduceCurrency) {
        balanceService.currencyExchange(traderId, count, addCurrency, reduceCurrency);
    }
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(ObjectNotFoundException objectNotFoundException){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(objectNotFoundException.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
