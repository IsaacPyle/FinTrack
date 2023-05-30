package com.fintrack.backend.controller;

import com.fintrack.backend.model.budget.Budget;
import com.fintrack.backend.service.BudgetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController("/budgets")
@RequiredArgsConstructor
@Validated
public class BudgetController {

    public final BudgetService budgetService;

    @PostMapping
    public ResponseEntity<Budget> createBudget(@RequestBody @Valid Budget budget) {
        return ResponseEntity.status(HttpStatus.CREATED).body(budgetService.putBudget(budget));
    }

    @GetMapping("/{budgetId}")
    public ResponseEntity<Budget> getBudget(@PathVariable("budgetId") UUID budgetId) {
        return budgetService.getBudgetByBudgetId(budgetId).map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
