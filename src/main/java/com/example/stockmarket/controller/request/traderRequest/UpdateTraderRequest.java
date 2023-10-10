package com.example.stockmarket.controller.request.traderRequest;

import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateTraderRequest extends TraderRequest {
    @NotNull
    private long id;
}
