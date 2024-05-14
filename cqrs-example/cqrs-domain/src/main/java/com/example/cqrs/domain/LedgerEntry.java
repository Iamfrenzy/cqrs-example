package com.example.cqrs.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class LedgerEntry {

    private UUID accountId;
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private TransactionType transactionType;

    private LedgerEntry() {
    }


    public UUID getAccountId() {
        return accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }


    public TransactionType getTransactionType() {
        return transactionType;
    }

    public static class Builder {
        private UUID accountId;
        private BigDecimal amount;
        private LocalDateTime timestamp = LocalDateTime.now();
        private TransactionType transactionType;

        public Builder(UUID accountId, BigDecimal amount) {
            this.accountId = accountId;
            this.amount = amount;
        }

        public Builder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder transactionType(TransactionType transactionType) {
            this.transactionType = transactionType;
            return this;
        }

        public LedgerEntry build() {
            LedgerEntry entry = new LedgerEntry();
            entry.accountId = this.accountId;
            entry.amount = this.amount;
            entry.timestamp = this.timestamp;
            entry.transactionType = this.transactionType;
            return entry;
        }
    }
}

