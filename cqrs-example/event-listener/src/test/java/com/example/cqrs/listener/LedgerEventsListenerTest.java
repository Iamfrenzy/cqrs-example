package com.example.cqrs.listener;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.example.cqrs.domain.LedgerEntry;
import com.example.cqrs.domain.TransactionType;
import com.example.cqrs.domain.dto.AccountTransaction;
import com.example.cqrs.domain.event.MoneyDepositedIntoAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LedgerEventsListenerTest {

    private LedgerEventsListener listener;
    private MoneyDepositedIntoAccount event1;
    private MoneyDepositedIntoAccount event2;
    private MoneyDepositedIntoAccount event3;
    private UUID accountId;

    @BeforeEach
    void beforEach() {
        listener = new LedgerEventsListener();
         accountId = UUID.randomUUID();
         event1 = new MoneyDepositedIntoAccount("One", new LedgerEntry.Builder(accountId, BigDecimal.valueOf(100))
                .transactionType(TransactionType.DEPOSIT)
                .build());
         event2 = new MoneyDepositedIntoAccount("Two", new LedgerEntry.Builder(accountId, BigDecimal.valueOf(50))
                .transactionType(TransactionType.WITHDRAWAL)
                .build());
         event3 = new MoneyDepositedIntoAccount("Three", new LedgerEntry.Builder(accountId, BigDecimal.valueOf(200))
                .transactionType(TransactionType.DEPOSIT)
                .build());

        listener.addLedgerEntry(event1);
        listener.addLedgerEntry(event2);
        listener.addLedgerEntry(event3);

    }

    @Test
    void shouldGetBalance() {
        BigDecimal expectedBalance = BigDecimal.valueOf(250);
        BigDecimal actualBalance = listener.getBalanceForAccount(accountId);

        assertEquals(expectedBalance, actualBalance);
    }

    @Test
    void shouldReturnTransactions() {

        final List<AccountTransaction> result = listener.getTransactions(accountId, 2);
        assertEquals(2, result.size());

    }
}
