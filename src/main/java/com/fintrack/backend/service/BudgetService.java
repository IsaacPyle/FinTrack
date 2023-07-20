package com.fintrack.backend.service;

import com.fintrack.backend.model.budget.Budget;
import com.fintrack.backend.repository.BudgetRepository;
import com.fintrack.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;

    public Optional<Budget> getBudgetByBudgetId(UUID budgetId) {
        return budgetRepository.findById(budgetId);
    }

    public Optional<Budget> getBudgetByUserId(UUID userId) {
        return userRepository.findById(userId)
            .flatMap(user -> budgetRepository.findById(user.getBudgetId()));
    }

    public Budget createNewBudget() {
        Budget budget = Budget.builder().build();

        return putBudget(budget);
    }

    public Budget putBudget(Budget budget) {
        return budgetRepository.save(budget);
    }

    public void deleteBudget(UUID budgetId) {
        budgetRepository.deleteById(budgetId);
    }
}
