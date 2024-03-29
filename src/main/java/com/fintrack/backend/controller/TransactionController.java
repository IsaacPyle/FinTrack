package com.fintrack.backend.controller;

import com.fintrack.backend.model.transaction.Transaction;
import com.fintrack.backend.service.BudgetService;
import com.fintrack.backend.service.TransactionService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
@Validated
@Slf4j
public class TransactionController {

    private final TransactionService transactionService;
    private final BudgetService budgetService;

    @PostMapping("/{userId}")
    public ResponseEntity<Transaction> createTransaction(@PathVariable("userId") UUID userId,
                                                         @RequestBody Transaction transaction) {
        log.info("Creating transaction for user with ID {}", userId);
        Transaction createdTransaction = transactionService.createTransaction(transaction);
        budgetService.getBudgetByUserId(userId).ifPresentOrElse(budget -> {
                List<UUID> transactions = Optional.ofNullable(budget.getTransactionIds()).orElse(new ArrayList<>());
                transactions.add(createdTransaction.getTransactionId());
                budget.setTransactionIds(transactions);
                budgetService.putBudget(budget);
            }, () -> {
                deleteTransactionById(createdTransaction.getTransactionId());
                throw new IllegalArgumentException(String.format("No user with id %s found to add transaction to.", userId));
            });

        return ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<Transaction> getTransaction(@PathVariable("transactionId") UUID transactionId) {
        log.info("Getting transaction with ID {}", transactionId);
        return transactionService.getTransactionById(transactionId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{userId}/{transactionId}")
    public ResponseEntity<?> deleteTransactionForUser(@PathVariable("userId") UUID userId,
                                               @PathVariable("transactionId") UUID transactionId) {
        log.info("Deleting transaction with ID {} for user with ID {}", transactionId, userId);
        budgetService.getBudgetByUserId(userId).ifPresent(budget ->
            transactionService.removeTransactionForBudget(budget, transactionId));

        return deleteTransactionById(transactionId);
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<?> deleteTransactionById(@PathVariable("transactionId") UUID transactionId) {
        log.info("Deleting transaction with ID {}", transactionId);
        transactionService.getTransactionById(transactionId)
            .map(Transaction::getTransactionId)
            .ifPresent(transactionService::deleteTransactionById);

        return ResponseEntity.noContent().build();
    }
}
