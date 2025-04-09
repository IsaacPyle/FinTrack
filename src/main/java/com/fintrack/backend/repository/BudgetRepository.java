package com.fintrack.backend.repository;

import com.fintrack.backend.model.budget.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, UUID> {}
