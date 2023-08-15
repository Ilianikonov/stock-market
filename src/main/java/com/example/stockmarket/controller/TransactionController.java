package com.example.stockmarket.controller;

import com.example.stockmarket.controller.response.ErrorResponse;
import com.example.stockmarket.exception.ObjectNotFoundException;
import com.example.stockmarket.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
@Slf4j
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/makeDepositing")
    public void makeDepositing(@RequestParam long traderId,
                               @RequestParam double amount,
                               @RequestParam String currency){
        transactionService.makeDepositing(traderId,amount,currency);
    }

    @PostMapping("/withdrawCurrency")
    public void withdrawCurrency(@RequestParam long traderId,
                                 @RequestParam double amount,
                                 @RequestParam String currency){
        transactionService.withdrawCurrency(traderId, amount, currency);
    }
    @PostMapping("/currencyExchange")
    public void currencyExchange(@RequestParam long traderId,
                                 @RequestParam double count,
                                 @RequestParam String givenCurrency,
                                 @RequestParam String receivedCurrency) {
        transactionService.currencyExchange(traderId, count, givenCurrency, receivedCurrency);
    }
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(ObjectNotFoundException objectNotFoundException){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(objectNotFoundException.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
