package com.fintrack.backend.controller;

import com.fintrack.backend.model.budget.Budget;
import com.fintrack.backend.service.BudgetService;
import com.fintrack.backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/budgets")
@RequiredArgsConstructor
@Validated
@Slf4j
public class BudgetController {

    private final BudgetService budgetService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Budget> createBudget(@RequestBody @Valid Budget budget) {
        return ResponseEntity.status(HttpStatus.CREATED).body(budgetService.putBudget(budget));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Budget> createBudgetForUser(@PathVariable("userId") UUID userId) {
        log.info("Creating budget for user with ID {}", userId);
        return userService.getUserById(userId)
            .map(user -> {
                Budget budget = budgetService.createNewBudget();
                userService.setBudgetIdForUser(userId, budget.getBudgetId());
                return ResponseEntity.status(HttpStatus.CREATED).body(budget);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{budgetId}")
    public ResponseEntity<Budget> getBudget(@PathVariable("budgetId") UUID budgetId) {
        return budgetService.getBudgetByBudgetId(budgetId).map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{budgetId}")
    public ResponseEntity<?> deleteBudgetForUser(@PathVariable("budgetId") UUID budgetId) {
        budgetService.deleteBudget(budgetId);

        return ResponseEntity.noContent().build();
    }
}
