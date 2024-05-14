package com.example.cqrs.listener;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.example.cqrs.domain.LedgerEntry;
import com.example.cqrs.domain.LedgerEntryEvent;
import com.example.cqrs.domain.TransactionType;
import com.example.cqrs.domain.dto.AccountTransaction;
import com.example.cqrs.domain.event.MoneyDepositedIntoAccount;
import com.example.cqrs.domain.event.MoneyWithdrawnFromAccount;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class LedgerEventsListener {

    private final Map<UUID, List<LedgerEntryEvent>> eventMap = new HashMap<>();

    @EventListener
    @Async
    void addLedgerEntry(MoneyDepositedIntoAccount event) {
        eventMap.computeIfAbsent(event.getEntry().getAccountId(), k -> new ArrayList<>()).add(event);
    }

    @EventListener
    @Async
    void addLedgerEntry(MoneyWithdrawnFromAccount event) {
        eventMap.computeIfAbsent(event.getEntry().getAccountId(), k -> new ArrayList<>()).add(event);
    }

    public BigDecimal getBalanceForAccount(UUID accountId) {
        List<LedgerEntryEvent> events = eventMap.getOrDefault(accountId, Collections.emptyList());
        BigDecimal balance = BigDecimal.ZERO;

        for (LedgerEntryEvent event : events) {
            TransactionType transactionType = event.getEntry().getTransactionType();
            BigDecimal amount = event.getEntry().getAmount();

            switch (transactionType) {
                case WITHDRAWAL:
                    balance = balance.subtract(amount);
                    break;
                case DEPOSIT:
                    balance = balance.add(amount);
                    break;
            }
        }

        return balance;
    }


    public List<AccountTransaction> getTransactions(UUID accountId, int numberOfTransactions) {

        List<AccountTransaction> accountTransactions = new ArrayList<>();

        List<LedgerEntryEvent> events = eventMap.getOrDefault(accountId, Collections.emptyList());

        List<LedgerEntryEvent> firstNevents = events.size() > numberOfTransactions ? events.subList(0, numberOfTransactions) : events;


        for (LedgerEntryEvent event : firstNevents) {
            TransactionType transactionType = event.getEntry().getTransactionType();
            final LedgerEntry entry = event.getEntry();
            BigDecimal amount = entry.getAmount();

            switch (transactionType) {
                case WITHDRAWAL:
                    accountTransactions.add(new AccountTransaction.Builder().accountId(entry.getAccountId().toString()).amount(" - " + entry.getAmount().toPlainString()).reference("Missing").transactionTime(entry.getTimestamp()).build());
                    break;
                case DEPOSIT:
                    accountTransactions.add(new AccountTransaction.Builder().accountId(entry.getAccountId().toString()).amount(" + " + entry.getAmount().toPlainString()).reference("Missing").transactionTime(entry.getTimestamp()).build());
                    break;
            }
        }
        return accountTransactions;

    }
}

