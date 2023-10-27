package com.ilianikonov.stockmarket.controller;

import com.ilianikonov.stockmarket.controller.converter.BalanceConverter;
import com.ilianikonov.stockmarket.controller.response.GetBalanceResponse;
import com.ilianikonov.stockmarket.entity.Balance;
import com.ilianikonov.stockmarket.service.BalanceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name="баланс", description="получает данные по балансу трейдера")
public class BalanceControllerImpl implements BalanceController {
    private final BalanceService balanceService;
    private final BalanceConverter balanceConverter;

    @RolesAllowed({"USER", "ADMIN"})
    public ResponseEntity<List<GetBalanceResponse>> getTotalBalance(long traderId){
        List<Balance> balances = balanceService.getTotalBalance(traderId);
        List<GetBalanceResponse> totalBalance = balanceConverter.balanceToGetBalanceResponse(balances);
        return new ResponseEntity<>(totalBalance, HttpStatus.OK);
    }

    @RolesAllowed({"USER", "ADMIN"})
    public ResponseEntity<GetBalanceResponse> getBalanceByCurrency(long traderId, String currency){
        Balance balance = balanceService.getBalanceByCurrency(traderId, currency);
        GetBalanceResponse getBalanceResponse = balanceConverter.balanceToGetBalanceResponse(balance);
        return new ResponseEntity<>(getBalanceResponse, HttpStatus.OK);
    }

    @RolesAllowed({"USER", "ADMIN"})
    public ResponseEntity<GetBalanceResponse> getTotalBalanceByCurreny(long traderId, String currency){
        double amount = balanceService.getTotalBalanceByCurrency(traderId, currency).getAmount();
        GetBalanceResponse getBalanceResponse = new GetBalanceResponse();
        getBalanceResponse.setCurrency(currency);
        getBalanceResponse.setAmount(amount);
        return new ResponseEntity<>(getBalanceResponse, HttpStatus.OK);
    }
}
