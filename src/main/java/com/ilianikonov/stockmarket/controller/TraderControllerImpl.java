package com.ilianikonov.stockmarket.controller;

import com.ilianikonov.stockmarket.controller.converter.TraderConverter;
import com.ilianikonov.stockmarket.controller.request.trader.CreateTraderRequest;
import com.ilianikonov.stockmarket.controller.request.trader.UpdateTraderRequest;
import com.ilianikonov.stockmarket.controller.response.TraderResponse;
import com.ilianikonov.stockmarket.entity.Trader;
import com.ilianikonov.stockmarket.service.TraderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name="Трейдер", description="монипулирует данными трейдера")
public class TraderControllerImpl implements TraderController {
    private  final TraderService traderService;
    private final TraderConverter traderConverter;

    @RolesAllowed({"USER", "ADMIN"})
    public TraderResponse createTrader(@Valid CreateTraderRequest createTraderRequest){
        Trader trader = traderService.createTrader(traderConverter.convertToTrader(createTraderRequest));
        return traderConverter.convertToTrader(trader);
    }

    @RolesAllowed({"USER", "ADMIN"})
    public TraderResponse updateTrader (UpdateTraderRequest updateTraderRequest) {
        Trader trader = traderService.updateTrader(traderConverter.convertToTrader(updateTraderRequest));
        return traderConverter.convertToTrader(trader);
    }

    @RolesAllowed({"ADMIN"})
    public TraderResponse deleteTraderById (Long id){
        Trader trader = traderService.deleteTraderById(id);
        return traderConverter.convertToTrader(trader);
    }

    @RolesAllowed({"USER", "ADMIN"})
    public ResponseEntity<TraderResponse> getTraderById(Long id) {
        Trader trader = traderService.getTraderById(id);
        if (trader == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(traderConverter.convertToTrader(trader));
    }
}
