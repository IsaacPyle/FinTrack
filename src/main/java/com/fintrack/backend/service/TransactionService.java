package com.fintrack.backend.service;

import com.fintrack.backend.model.transaction.Transaction;

import java.util.Optional;

public class TransactionService {

    public Optional<Transaction> getTransactionById(String transactionId) {
        return Optional.of(Transaction.builder().build());
    }
}
