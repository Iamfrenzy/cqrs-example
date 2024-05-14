package com.example.cqrs.domain.aggregate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.cqrs.domain.LedgerEntry;
import com.example.cqrs.domain.LedgerEntryEvent;
import com.example.cqrs.domain.TransactionType;
import com.example.cqrs.domain.event.MoneyDepositedIntoAccount;
import com.example.cqrs.domain.event.MoneyWithdrawnFromAccount;
import com.example.cqrs.domain.event.handler.DepositMoney;
import com.example.cqrs.domain.event.handler.WithdrawMoney;

public class Account {
    private UUID accountId;
    private BigDecimal balance = new BigDecimal(0);
    private List<LedgerEntry> ledgerEntries = new ArrayList<>();

    void apply(LedgerEntry entry){
        this.accountId= entry.getAccountId();
        applyEntry(entry);
        addEntry(entry);
    }


    private void addEntry(LedgerEntry entry) {
        ledgerEntries.add(entry);
    }

    private void applyEntry(LedgerEntry entry) {
        TransactionType theType = entry.getTransactionType();
        switch (theType) {
            case DEPOSIT -> balance = balance.add(entry.getAmount());
            case WITHDRAWAL -> balance = balance.subtract(entry.getAmount());
        }
    }

    public MoneyDepositedIntoAccount depositMoney(final DepositMoney depositMoney) {
        LedgerEntry ledgerEntry = new LedgerEntry.Builder(depositMoney.getAccountId(),
                depositMoney.getAmount()).transactionType(TransactionType.DEPOSIT).build();
        apply(ledgerEntry);
        return new MoneyDepositedIntoAccount(this,ledgerEntry);
    }


    public MoneyWithdrawnFromAccount withdrawMoney(final WithdrawMoney withdrawMoney) {
        LedgerEntry ledgerEntry = new LedgerEntry.Builder(withdrawMoney.getAccountId(),
                withdrawMoney.getAmount()).transactionType(TransactionType.WITHDRAWAL).build();
        apply(ledgerEntry);
        return new MoneyWithdrawnFromAccount(this,ledgerEntry);
    }

    public void replayEvents(final List<LedgerEntryEvent> events) {
            for (LedgerEntryEvent event : events) {
                if (event instanceof MoneyDepositedIntoAccount) {
                    balance = balance.add(((MoneyDepositedIntoAccount) event).getEntry().getAmount());
                }
            }
        }


}

