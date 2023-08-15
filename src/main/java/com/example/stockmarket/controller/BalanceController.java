package com.example.stockmarket.controller;

import com.example.stockmarket.controller.response.ErrorResponse;
import com.example.stockmarket.controller.response.GetBalanceResponse;
import com.example.stockmarket.exception.ObjectNotFoundException;
import com.example.stockmarket.service.BalanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/balance")
@Slf4j
@RequiredArgsConstructor
public class BalanceController {
    private final BalanceService balanceService;

    @GetMapping("/getTotalBalance")
    public ResponseEntity<GetBalanceResponse> getTotalBalance(@RequestParam long traderId,
                                                              @RequestParam String currency){
        double amount = balanceService.getTotalBalance(traderId, currency).getAmount();
        GetBalanceResponse getBalanceResponse = new GetBalanceResponse();
        getBalanceResponse.setCurrency(currency);
        getBalanceResponse.setAmount(amount);
        return new ResponseEntity<>(getBalanceResponse, HttpStatus.OK);
    }
    @GetMapping("/getBalanceByCurrency")
    public ResponseEntity<GetBalanceResponse> getBalanceByCurrency(@RequestParam long traderId,
                                                                   @RequestParam String currency){
        double amount = balanceService.getBalanceByCurrency(traderId, currency).getAmount();
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
