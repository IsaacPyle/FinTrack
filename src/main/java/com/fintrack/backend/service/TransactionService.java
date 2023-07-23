package com.fintrack.backend.service;

import com.fintrack.backend.model.budget.Budget;
import com.fintrack.backend.model.transaction.Transaction;
import com.fintrack.backend.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final BudgetService budgetService;

    public Optional<Transaction> getTransactionById(UUID transactionId) {
        return transactionRepository.findById(transactionId);
    }

    public Transaction createTransaction(Transaction transaction) {
        log.info("Creating transaction in service with ID {}", transaction.getTransactionId());
        return transactionRepository.save(transaction);
    }

    public void deleteTransactionById(UUID transactionId) {
        transactionRepository.deleteById(transactionId);
        log.info("Deleted transaction from repo with ID {}", transactionId);
    }

    public void removeTransactionForBudget(Budget budget, UUID transactionId) {
        List<UUID> transactionIds = budget.getTransactionIds();
        if (transactionIds.remove(transactionId)) {
            budget.setTransactionIds(transactionIds);
            budgetService.putBudget(budget);
        }
    }
}
