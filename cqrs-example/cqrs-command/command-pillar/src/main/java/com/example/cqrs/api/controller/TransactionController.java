package com.example.cqrs.api.controller;

import com.example.cqrs.api.domain.TransactionRequest;
import com.example.cqrs.domain.event.handler.DepositMoney;
import com.example.cqrs.domain.event.handler.WithdrawMoney;
import com.example.cqrs.handler.TransactionCommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import javax.validation.Valid;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionCommandHandler service;

    @Autowired
    private  ApplicationEventPublisher eventPublisher;

    @PostMapping("/deposit/{accountId}")
    public ResponseEntity<String> deposit(@Valid @RequestBody TransactionRequest request , @PathVariable UUID accountId) {
        eventPublisher.publishEvent(new DepositMoney("TransactionController.deposit",accountId,request.getAmount()));
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
    @PostMapping("/withdraw/{accountId}")
    public ResponseEntity<String> withdraw(@Valid @RequestBody TransactionRequest request , @PathVariable UUID accountId) {
        eventPublisher.publishEvent(new WithdrawMoney("TransactionController.withdraw",accountId,request.getAmount()));
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }


}

