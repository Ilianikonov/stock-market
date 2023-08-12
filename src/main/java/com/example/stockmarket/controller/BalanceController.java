package com.example.stockmarket.controller;

import com.example.stockmarket.controller.response.ErrorResponse;
import com.example.stockmarket.controller.response.GetBalanceResponse;
import com.example.stockmarket.exception.ObjectNotFoundException;
import com.example.stockmarket.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/balance")
@Slf4j
public class BalanceController {
    private final TransactionService transactionService;

    public BalanceController(TransactionService transactionService) {
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
                                 @RequestParam String addCurrency,
                                 @RequestParam String reduceCurrency) {
        transactionService.currencyExchange(traderId, count, addCurrency, reduceCurrency);
    }
    @GetMapping("/getTotalBalance")
    public ResponseEntity<GetBalanceResponse> getTotalBalance(@RequestParam long traderId,
                                                              @RequestParam String currency){
        double amount = transactionService.getTotalBalance(traderId, currency).getAmount();
        GetBalanceResponse getBalanceResponse = new GetBalanceResponse();
        getBalanceResponse.setCurrency(currency);
        getBalanceResponse.setAmount(amount);
        return new ResponseEntity<>(getBalanceResponse, HttpStatus.OK);
    }
    @GetMapping("/getBalanceByCurrency")
    public ResponseEntity<GetBalanceResponse> getBalanceByCurrency(@RequestParam long traderId,
                                                                   @RequestParam String currency){
        double amount = transactionService.getBalanceByCurrency(traderId, currency).getAmount();
        GetBalanceResponse getBalanceResponse = new GetBalanceResponse();
        getBalanceResponse.setCurrency(currency);
        getBalanceResponse.setAmount(amount);
        return new ResponseEntity<>(getBalanceResponse, HttpStatus.OK);
    }
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(ObjectNotFoundException objectNotFoundException){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(objectNotFoundException.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
