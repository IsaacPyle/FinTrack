package com.fintrack.backend.repository;

import com.fintrack.backend.model.budget.Budget;

public class BudgetRepository {

    public Budget getBudgetById(String budgetId) {
        return Budget.builder().build();
    }
}
