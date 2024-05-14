package com.example.cqrs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.example.cqrs.domain.LedgerEntryEvent;
import com.example.cqrs.domain.event.MoneyDepositedIntoAccount;
import com.example.cqrs.domain.event.MoneyWithdrawnFromAccount;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AccountEventStore implements EventStore {

    private final Map<UUID,List<LedgerEntryEvent>> eventMap = new HashMap<>();
    @Override
    public Optional<List<LedgerEntryEvent>> getEventsForAggregate(final UUID accountId) {
        return Optional.ofNullable(eventMap.get(accountId));
    }

    @EventListener
    public void addLedgerEntry(MoneyDepositedIntoAccount event) {
        eventMap.computeIfAbsent(event.getEntry().getAccountId(), k -> new ArrayList<>()).add(event);
    }

    @EventListener
    public void addLedgerEntry(MoneyWithdrawnFromAccount event) {
        eventMap.computeIfAbsent(event.getEntry().getAccountId(), k -> new ArrayList<>()).add(event);
    }

}
