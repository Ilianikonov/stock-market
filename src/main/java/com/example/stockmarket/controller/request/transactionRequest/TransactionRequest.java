package com.example.stockmarket.controller.request.transactionRequest;

import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;
@Data
public abstract class TransactionRequest {
    private long traderId;
}
