package com.example.cqrs.handler;

import java.math.BigDecimal;
import java.util.UUID;

import com.example.cqrs.AggregateRepository;
import com.example.cqrs.domain.LedgerEntry;
import com.example.cqrs.domain.TransactionType;
import com.example.cqrs.domain.aggregate.Account;
import com.example.cqrs.domain.event.MoneyDepositedIntoAccount;
import com.example.cqrs.domain.event.MoneyWithdrawnFromAccount;
import com.example.cqrs.domain.event.handler.DepositMoney;
import com.example.cqrs.domain.event.handler.WithdrawMoney;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

@ExtendWith(MockitoExtension.class)
class TransactionCommandHandlerTest {
    @Mock
    private Account account;
    @Mock
    private AggregateRepository repository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private TransactionCommandHandler handler;

    @Test
    void shouldDepositMoney() {
        //Given
        final UUID accountId = UUID.randomUUID();
        final MoneyDepositedIntoAccount event = new MoneyDepositedIntoAccount("Money will be added",
                new LedgerEntry.Builder(accountId, new BigDecimal("10.0")).transactionType(TransactionType.DEPOSIT).build());
        final DepositMoney depositMoney = new DepositMoney("Anysource", accountId, new BigDecimal(20));

        Mockito.when(repository.fetchAccount(Mockito.eq(accountId))).thenReturn(account);
        Mockito.when(account.depositMoney(Mockito.eq(depositMoney))).thenReturn(event);
        Mockito.doNothing().when(eventPublisher).publishEvent(event);

        //When
        handler.depositMoney(depositMoney);

        //Then
        Mockito.verify(repository).fetchAccount(accountId);
        Mockito.verify(account).depositMoney(depositMoney);
    }


    @Test
    void shouldWithdrawMoney() {
        //Given
        final UUID accountId = UUID.randomUUID();
        final MoneyWithdrawnFromAccount event = new MoneyWithdrawnFromAccount("Money will be withdrawn",
                new LedgerEntry.Builder(accountId, new BigDecimal("10.0")).transactionType(TransactionType.WITHDRAWAL).build());
        final WithdrawMoney money = new WithdrawMoney("Anysource", accountId, new BigDecimal(20));

        Mockito.when(repository.fetchAccount(Mockito.eq(accountId))).thenReturn(account);
        Mockito.when(account.withdrawMoney(Mockito.eq(money))).thenReturn(event);
        Mockito.doNothing().when(eventPublisher).publishEvent(event);

        //When
        handler.withdrawMoney(money);

        //Then
        Mockito.verify(repository).fetchAccount(accountId);
        Mockito.verify(account).withdrawMoney(money);
    }

}