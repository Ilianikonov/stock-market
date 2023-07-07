package com.example.stockmarket.controller.request;

import com.example.stockmarket.entity.Balance;
import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateTraderRequest extends TraderRequest {
    @NotNull
    private long id;
}
