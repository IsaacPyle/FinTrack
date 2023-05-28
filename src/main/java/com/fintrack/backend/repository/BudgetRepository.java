package com.fintrack.backend.repository;

import com.fintrack.backend.model.budget.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BudgetRepository extends JpaRepository<Budget, UUID> {}
