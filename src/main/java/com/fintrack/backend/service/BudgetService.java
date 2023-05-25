package com.fintrack.backend.service;

import com.fintrack.backend.model.budget.Budget;
import com.fintrack.backend.repository.BudgetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;

    public Optional<Budget> getBudgetById(String budgetId) {
        return Optional.ofNullable(budgetRepository.getBudgetById(budgetId));
    }
}
