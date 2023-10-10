package com.example.stockmarket.controller.request.traderRequest;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
@Data
public abstract class TraderRequest {
    @Size(max = 20)
    private String name;
    @Size(min = 6)
    @Size(max = 100)
    private char[] password;
    private boolean enabled = true;
    private List <String> roles;
}
