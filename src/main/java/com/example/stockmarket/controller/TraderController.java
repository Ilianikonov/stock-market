package com.example.stockmarket.controller;

import com.example.stockmarket.controller.request.trader.CreateTraderRequest;
import com.example.stockmarket.controller.request.trader.UpdateTraderRequest;
import com.example.stockmarket.controller.response.TraderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/trader")
public interface TraderController {

    @Operation(summary = "создание trader", description = "Позволяет создать трейдера")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Created Trader",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = TraderResponse.class)))
                    })
    })
    @PostMapping("/createTrader")
    TraderResponse createTrader(@RequestBody CreateTraderRequest createTraderRequest);
    @Operation(summary = "обновить trader", description = "Позволяет обновить все данные трейдера")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Updated Trader",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = TraderResponse.class)))
                    })
    })
    @PostMapping("/updateTrader")
    TraderResponse updateTrader (@RequestBody UpdateTraderRequest updateTraderRequest);

    @Operation(summary = "удалить trader", description = "Позволяет удалить трейдера по id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Deleted Trader",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = TraderResponse.class)))
                    })
    })
    @DeleteMapping("/deleteTraderById/{id}")
    TraderResponse deleteTraderById (@PathVariable Long id);

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the Trader",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = TraderResponse.class)))
                    })
    })
    @Operation(summary = "олучить trader по id", description = "Позволяет получить все данные трейдера")
    @GetMapping("/getTraderById/{id}")
    ResponseEntity<TraderResponse> getTraderById(@PathVariable Long id);
}
