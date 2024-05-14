package com.example.cqrs;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.example.cqrs.domain.dto.AccountTransaction;
import com.example.cqrs.listener.LedgerEventsListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountViewController {


    @Autowired
    private LedgerEventsListener ledgerEventsListener;

    @GetMapping("/balance/{accountId}")
    public ResponseEntity<String> getbalance(@PathVariable("accountId") String accountId){
        final BigDecimal balance = ledgerEventsListener.getBalanceForAccount(UUID.fromString(accountId));
        return ResponseEntity.status(HttpStatus.OK).body(balance.toPlainString());
    }

    @GetMapping("/transactions/{accountId}")
    public ResponseEntity<List<AccountTransaction>> getTransactions(@PathVariable("accountId") String accountId,
                                                                    @RequestParam(value = "noOfTransactions", required = false, defaultValue = "30")  int  noOfTransactions){
        final List<AccountTransaction> transactions = ledgerEventsListener.getTransactions(UUID.fromString(accountId), noOfTransactions);
        return ResponseEntity.status(HttpStatus.OK).body(transactions);
    }

}
