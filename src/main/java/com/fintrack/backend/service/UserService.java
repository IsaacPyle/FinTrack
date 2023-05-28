package com.fintrack.backend.service;

import com.fintrack.backend.model.user.User;
import com.fintrack.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> getUserById(UUID userId) {
        return userRepository.findById(userId);
    }

    public User createUserById() {
        User newUser = User.builder()
            .userId(UUID.randomUUID())
            .build();
        return userRepository.save(newUser);
    }

    public void setBudgetIdForUser(UUID userId, UUID budgetId) {
        getUserById(userId).ifPresentOrElse(user -> {
            user.setBudgetId(budgetId);
            userRepository.save(user);
        }, () -> {
            throw new NoSuchElementException(String.format("User with userId %s not found", userId));
        });
    }
}
