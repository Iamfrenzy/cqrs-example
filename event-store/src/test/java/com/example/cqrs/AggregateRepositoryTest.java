package com.example.cqrs;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.UUID;

import com.example.cqrs.domain.LedgerEntry;
import com.example.cqrs.domain.LedgerEntryEvent;
import com.example.cqrs.domain.TransactionType;
import com.example.cqrs.domain.event.MoneyDepositedIntoAccount;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AggregateRepositoryTest {
    @Mock
     private EventStore accountEventStore;

    @InjectMocks
    private  AggregateRepository repository;





}