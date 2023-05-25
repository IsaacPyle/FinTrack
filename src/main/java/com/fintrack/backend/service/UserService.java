package com.fintrack.backend.service;

import com.fintrack.backend.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    public Optional<User> getUserById(String userId) {
        return Optional.of(User.builder().build());
    }
}
