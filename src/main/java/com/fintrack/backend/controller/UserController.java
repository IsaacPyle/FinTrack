package com.fintrack.backend.controller;

import com.fintrack.backend.model.user.User;
import com.fintrack.backend.service.BudgetService;
import com.fintrack.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final BudgetService budgetService;

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser() {
        User user = userService.createUser();

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") UUID userId) {
        return userService.getUserById(userId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable("userId") UUID userId) {
        userService.getUserById(userId)
            .map(User::getBudgetId)
            .ifPresent(budgetService::deleteBudget);
        userService.deleteUserById(userId);

        return ResponseEntity.noContent().build();
    }


}
