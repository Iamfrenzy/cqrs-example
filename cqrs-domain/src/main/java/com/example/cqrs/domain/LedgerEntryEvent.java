package com.example.cqrs.domain;

public interface LedgerEntryEvent {

    public LedgerEntry getEntry();
}
