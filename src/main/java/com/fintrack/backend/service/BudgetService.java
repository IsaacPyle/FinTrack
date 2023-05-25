package com.fintrack.backend.service;

import com.fintrack.backend.model.budget.Budget;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BudgetService {

    public Optional<Budget> getBudgetById(String budgetId) {
        return Optional.of(new Budget());
    }
}
