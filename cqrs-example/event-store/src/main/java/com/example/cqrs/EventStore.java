package com.example.cqrs;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.cqrs.domain.LedgerEntry;
import com.example.cqrs.domain.LedgerEntryEvent;
import com.example.cqrs.domain.event.MoneyDepositedIntoAccount;

public interface EventStore {

    Optional<List<LedgerEntryEvent>> getEventsForAggregate(UUID accountId);
    void addLedgerEntry(MoneyDepositedIntoAccount moneyDepositedIntoAccount);

}
