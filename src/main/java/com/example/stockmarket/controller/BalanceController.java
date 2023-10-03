package com.example.stockmarket.controller;

import com.example.stockmarket.controller.converter.BalanceConverter;
import com.example.stockmarket.controller.response.GetBalanceResponse;
import com.example.stockmarket.entity.Balance;
import com.example.stockmarket.service.BalanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/balance")
@Slf4j
@RequiredArgsConstructor
public class BalanceController {
    private final BalanceService balanceService;
    private final BalanceConverter balanceConverter;

    @GetMapping("/getTotalBalance")
    public ResponseEntity<List<GetBalanceResponse>> getTotalBalance(@RequestParam long traderId){
        List<Balance> balances = balanceService.getTotalBalance(traderId);
        List<GetBalanceResponse> totalBalance = balanceConverter.balanceToGetBalanceResponse(balances);
        return new ResponseEntity<>(totalBalance, HttpStatus.OK);
    }

    @GetMapping("/getBalanceByCurrency")
    public ResponseEntity<GetBalanceResponse> getBalanceByCurrency(@RequestParam long traderId,
                                                                   @RequestParam String currency){
        Balance balance = balanceService.getBalanceByCurrency(traderId, currency);
        GetBalanceResponse getBalanceResponse = balanceConverter.balanceToGetBalanceResponse(balance);
        return new ResponseEntity<>(getBalanceResponse, HttpStatus.OK);
    }
    @GetMapping("/getTotalBalanceByCurreny")
    public ResponseEntity<GetBalanceResponse> getTotalBalanceByCurreny(@RequestParam long traderId,
                                                              @RequestParam String currency){
        double amount = balanceService.getTotalBalanceByCurrency(traderId, currency).getAmount();
        GetBalanceResponse getBalanceResponse = new GetBalanceResponse();
        getBalanceResponse.setCurrency(currency);
        getBalanceResponse.setAmount(amount);
        return new ResponseEntity<>(getBalanceResponse, HttpStatus.OK);
    }
}
