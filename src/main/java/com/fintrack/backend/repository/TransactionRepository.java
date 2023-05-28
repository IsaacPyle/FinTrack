package com.fintrack.backend.repository;

import com.fintrack.backend.model.budget.Budget;
import com.fintrack.backend.model.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {}
