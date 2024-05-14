package com.example.cqrs.domain.event.handler;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.context.ApplicationEvent;

public class DepositMoney extends ApplicationEvent {

    private UUID accountId;
    private BigDecimal amount;

    public DepositMoney(final Object source, UUID accountId, BigDecimal amount) {
        super(source);
        this.accountId = accountId;
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public UUID getAccountId() {
        return accountId;
    }
}
