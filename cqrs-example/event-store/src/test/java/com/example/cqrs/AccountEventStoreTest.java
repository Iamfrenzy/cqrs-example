package com.example.cqrs;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.cqrs.domain.LedgerEntry;
import com.example.cqrs.domain.LedgerEntryEvent;
import com.example.cqrs.domain.TransactionType;
import com.example.cqrs.domain.event.MoneyDepositedIntoAccount;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

        import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

 class AccountEventStoreTest {

    private AccountEventStore accountEventStore;

    @BeforeEach
    public void setUp() {
        accountEventStore = new AccountEventStore();
    }

    @Test
    public void shouldfetchEevents() {
        // Given
        UUID accountId = UUID.randomUUID();
        List<LedgerEntryEvent> events = new ArrayList<>();
        MoneyDepositedIntoAccount event1 =new MoneyDepositedIntoAccount("Money will be added",
                new LedgerEntry.Builder(accountId, new BigDecimal("10.0")).transactionType(TransactionType.DEPOSIT).build());
        MoneyDepositedIntoAccount event2 =new MoneyDepositedIntoAccount("Money will be added",
                new LedgerEntry.Builder(accountId, new BigDecimal("30.0")).transactionType(TransactionType.DEPOSIT).build());
        events.add(event1);
        events.add(event2);
        accountEventStore.addLedgerEntry(event1);
        accountEventStore.addLedgerEntry(event2);

        // When
        List<LedgerEntryEvent> fetchedEvents = accountEventStore.getEventsForAggregate(accountId).get();

        // Then
        assertNotNull(fetchedEvents);
        assertEquals(2, fetchedEvents.size());
        assertTrue(fetchedEvents.contains(event1));
        assertTrue(fetchedEvents.contains(event2));
    }

    @Test
    public void testGetEventsForAggregate_WhenNoEventsExist() {
        // Given
        UUID accountId = UUID.randomUUID();

        // When
        Optional<List<LedgerEntryEvent>> fetchedEvents = accountEventStore.getEventsForAggregate(accountId);

        // Then
        assertFalse(fetchedEvents.isPresent());
    }
}
