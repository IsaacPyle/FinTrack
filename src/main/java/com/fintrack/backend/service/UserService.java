package com.fintrack.backend.service;

import com.fintrack.backend.model.user.User;
import com.fintrack.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> getUserById(UUID userId) {
        return userRepository.findById(userId);
    }

    public User createUser(User user) {
        User newUser = User.builder()
            .userId(UUID.randomUUID())
            .name(user.getName())
            .build();
        return userRepository.save(newUser);
    }

    public void setBudgetIdForUser(UUID userId, UUID budgetId) {
        getUserById(userId).ifPresentOrElse(user -> {
            log.info("Adding budgetID {} to user with ID {}", budgetId, userId);
            user.setBudgetId(budgetId);
            userRepository.save(user);
        }, () -> {
            throw new NoSuchElementException(String.format("User with userId %s not found", userId));
        });
    }

    public void deleteUserById(UUID userId) {
        userRepository.deleteById(userId);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
