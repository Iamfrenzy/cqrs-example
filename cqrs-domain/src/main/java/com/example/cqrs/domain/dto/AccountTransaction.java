package com.example.cqrs.domain.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.time.LocalDateTime;

@JsonDeserialize(builder = AccountTransaction.Builder.class)
public class AccountTransaction {

    private final String accountId;
    private final String amount;
    private final LocalDateTime transactionTime;
    private final String reference;

    private AccountTransaction(Builder builder) {
        this.accountId = builder.accountId;
        this.amount = builder.amount;
        this.transactionTime = builder.transactionTime;
        this.reference = builder.reference;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
        private String accountId;
        private String amount;
        private LocalDateTime transactionTime;
        private String reference;

        public Builder accountId(String accountId) {
            this.accountId = accountId;
            return this;
        }

        public Builder amount(String amount) {
            this.amount = amount;
            return this;
        }

        public Builder transactionTime(LocalDateTime transactionTime) {
            this.transactionTime = transactionTime;
            return this;
        }

        public Builder reference(String reference) {
            this.reference = reference;
            return this;
        }

        public AccountTransaction build() {
            return new AccountTransaction(this);
        }
    }

    // Getter methods

    public String getAccountId() {
        return accountId;
    }

    public String getAmount() {
        return amount;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public String getReference() {
        return reference;
    }

}
