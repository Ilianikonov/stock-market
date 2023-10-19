package com.ilianikonov.stockmarket.controller;

import com.ilianikonov.stockmarket.controller.response.GetBalanceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@RequestMapping("/balance")
public interface BalanceController {

    @Operation(summary = "получение всех балансов", description = "Позволяет получить баланс всех имеющихся валют")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "received the balance of all available currencies",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = GetBalanceResponse.class)))
                    })
    })
    @GetMapping("/getTotalBalance")
    ResponseEntity<List<GetBalanceResponse>> getTotalBalance(@RequestParam long traderId);

    @Operation(summary = "получение баланса валюты", description = "Позволяет получить баланс одной указаной валюты")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "received currency balance",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = GetBalanceResponse.class)))
                    })
    })
    @GetMapping("/getBalanceByCurrency")
    ResponseEntity<GetBalanceResponse> getBalanceByCurrency(@RequestParam long traderId, @RequestParam String currency);

    @Operation(summary = "получение всех балансов в одной валюте", description = "Позволяет получить общий баланс в одной указаной валюте")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "received the total balance in foreign currency",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = GetBalanceResponse.class)))
                    })
    })
    @GetMapping("/getTotalBalanceByCurreny")
    ResponseEntity<GetBalanceResponse> getTotalBalanceByCurreny(@RequestParam long traderId, @RequestParam String currency);
}
