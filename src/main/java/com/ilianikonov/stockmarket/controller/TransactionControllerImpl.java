package com.ilianikonov.stockmarket.controller;

import com.ilianikonov.stockmarket.controller.request.transaction.CurrencyExchangeRequest;
import com.ilianikonov.stockmarket.controller.request.transaction.MakeDepositingRequest;
import com.ilianikonov.stockmarket.controller.request.transaction.WithdrawCurrencyRequest;
import com.ilianikonov.stockmarket.service.TransactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name="Транзакция", description="управление средствами трейдера")
public class TransactionControllerImpl implements TransactionController {
    private final TransactionService transactionService;

    @RolesAllowed({"USER", "ADMIN"})
    public void makeDepositing(MakeDepositingRequest makeDepositingRequest){
        transactionService.makeDepositing(makeDepositingRequest.getTraderId(), makeDepositingRequest.getReceivedAmount(), makeDepositingRequest.getReceivedCurrency());
    }

    @RolesAllowed({"USER", "ADMIN"})
    public void withdrawCurrency(WithdrawCurrencyRequest withdrawCurrencyRequest){
        transactionService.withdrawCurrency(withdrawCurrencyRequest.getTraderId(), withdrawCurrencyRequest.getGivenAmount(), withdrawCurrencyRequest.getGivenCurrency());
    }

    @RolesAllowed({"USER", "ADMIN"})
    public void currencyExchange(CurrencyExchangeRequest currencyExchangeRequest) {
        transactionService.currencyExchange(currencyExchangeRequest.getTraderId(), currencyExchangeRequest.getGivenAmount(), currencyExchangeRequest.getGivenCurrency(), currencyExchangeRequest.getReceivedCurrency());
    }

}
