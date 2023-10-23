package com.ilianikonov.stockmarket.controller.request.trader;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
@Data
public abstract class TraderRequest {
    @Size(max = 20)
    @Schema(description = "уникальное имя трейдера",
            name = "name",
            type = "string")
    private String name;
    @Size(min = 6)
    @Size(max = 100)
    @Schema(description = "пароль трейдера",
            name = "password",
            type = "string")
    private char[] password;
    @Schema(description = "индикатор доступа",
            name = "enabled",
            type = "boolean")
    private boolean enabled = true;
    @Schema(description = "роли трейдера",
            name = "roles",
            type = "[string[]]")
    private List <String> roles;
}
