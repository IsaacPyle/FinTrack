package com.fintrack.backend.repository;

import com.fintrack.backend.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {}
