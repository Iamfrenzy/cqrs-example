package com.example.cqrs.api.domain;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

public class TransactionRequest {

    @NotNull
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
