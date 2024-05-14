package com.example.cqrs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.example.cqrs.domain.LedgerEntryEvent;
import com.example.cqrs.domain.aggregate.Account;
import com.example.cqrs.domain.event.MoneyDepositedIntoAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Repository;

@Repository
public class AggregateRepository {
    @Autowired
    private EventStore accountEventStore;

    public Account fetchAccount(UUID accountid) {
        Optional<List<LedgerEntryEvent>> events = accountEventStore.getEventsForAggregate(accountid);
        Account aggregate = new Account();
        events.ifPresent(aggregate::replayEvents);
        return aggregate;
    }

}
