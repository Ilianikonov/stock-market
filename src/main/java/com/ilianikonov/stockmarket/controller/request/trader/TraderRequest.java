package com.ilianikonov.stockmarket.controller.request.trader;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
@Data
public abstract class TraderRequest {
    @Size(max = 20)
    @Schema
    private String name;
    @Size(min = 6)
    @Size(max = 100)
    @Schema
    private char[] password;
    @Schema
    private boolean enabled = true;
    @Schema
    private List <String> roles;
}
