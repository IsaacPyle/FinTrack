package com.fintrack.backend.service;

import com.fintrack.backend.model.budget.Budget;
import com.fintrack.backend.repository.BudgetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private BudgetRepository budgetRepository;

    public Optional<Budget> getBudgetById(UUID budgetId) {
        return budgetRepository.findById(budgetId);
    }

    public Budget createBudget() {
        Budget budget = Budget.builder().build();

        return budgetRepository.save(budget);
    }

    public void deleteBudget(UUID budgetId) {
        budgetRepository.deleteById(budgetId);
    }
}
