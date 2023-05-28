package com.fintrack.backend.controller;

import com.fintrack.backend.model.budget.Budget;
import com.fintrack.backend.model.user.User;
import com.fintrack.backend.service.BudgetService;
import com.fintrack.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController("/users")
@RequiredArgsConstructor
public class UserController {

    private final BudgetService budgetService;

    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<User> createUse() {
        User user = userService.createUserById();

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Budget> createBudgetForUser(@PathVariable("userId") UUID userId) {
        return userService.getUserById(userId)
            .map(user -> {
                Budget budget = budgetService.createBudget();
                user.setBudgetId(budget.getBudgetId());
                userService.setBudgetIdForUser(userId, budget.getBudgetId());
                return ResponseEntity.status(HttpStatus.CREATED).body(budget);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") UUID userId) {
        return userService.getUserById(userId).map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
