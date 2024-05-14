package com.example.cqrs.domain.event;

import com.example.cqrs.domain.LedgerEntry;
import com.example.cqrs.domain.LedgerEntryEvent;
import org.springframework.context.ApplicationEvent;

public class MoneyDepositedIntoAccount extends ApplicationEvent  implements LedgerEntryEvent {

    LedgerEntry entry ;
    public MoneyDepositedIntoAccount(final Object source, LedgerEntry entry ) {
        super(source);
        this.entry = entry;
    }

    public LedgerEntry getEntry() {
        return entry;
    }
}
