package com.fintrack.backend.controller;

import com.fintrack.backend.model.transaction.Transaction;
import com.fintrack.backend.service.BudgetService;
import com.fintrack.backend.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController("/transactions")
@RequiredArgsConstructor
@Validated
@Slf4j
public class TransactionController {

    private final TransactionService transactionService;
    private final BudgetService budgetService;

    @PostMapping("/{userId}")
    public ResponseEntity<Transaction> createTransaction(@PathVariable("userId") UUID userId,
                                                         @Valid @RequestBody Transaction transaction) {
        log.info("Creating transaction with ID {} for user with ID {}", transaction.getTransactionId(), userId);
        budgetService.getBudgetByUserId(userId).ifPresentOrElse(budget -> {
                List<UUID> transactions = budget.getTransactionIds();
                transactions.add(transaction.getTransactionId());
                budget.setTransactionIds(transactions);
            }, () -> {
                throw new IllegalArgumentException(String.format("No user with id %s found to add transaction to.", userId));
            });

        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.createTransaction(transaction));
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<Transaction> getTransaction(@PathVariable("transactionId") UUID transactionId) {
        return transactionService.getTransactionById(transactionId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{userId}/{transactionId}")
    public ResponseEntity<?> deleteTransaction(@PathVariable("userId") UUID userId,
                                               @PathVariable("transactionId") UUID transactionId) {
        budgetService.getBudgetByUserId(userId).ifPresent(budget -> {
            List<UUID> transactionIds = budget.getTransactionIds();
            if (transactionIds.remove(transactionId)) {
                budget.setTransactionIds(transactionIds);
                budgetService.putBudget(budget);
            }
        });

        transactionService.getTransactionById(transactionId)
            .map(Transaction::getTransactionId)
            .flatMap(transactionService::getTransactionById)
            .map(Transaction::getTransactionId)
            .ifPresent(transactionService::deleteTransactionById);

        return ResponseEntity.noContent().build();
    }
}
