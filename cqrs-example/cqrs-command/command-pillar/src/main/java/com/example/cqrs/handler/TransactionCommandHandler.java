package com.example.cqrs.handler;

import com.example.cqrs.AggregateRepository;
import com.example.cqrs.domain.aggregate.Account;
import com.example.cqrs.domain.event.handler.DepositMoney;
import com.example.cqrs.domain.event.handler.WithdrawMoney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class TransactionCommandHandler {

    @Autowired
    private AggregateRepository repository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @EventListener
    public void depositMoney(DepositMoney handler) {
        Account account = repository.fetchAccount(handler.getAccountId());
        eventPublisher.publishEvent(account.depositMoney(handler));
    }


    @EventListener
    public void withdrawMoney(WithdrawMoney handler) {
        Account account = repository.fetchAccount(handler.getAccountId());
        eventPublisher.publishEvent(account.withdrawMoney(handler));
    }

}
