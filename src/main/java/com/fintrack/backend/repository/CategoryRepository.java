package com.fintrack.backend.repository;

import com.fintrack.backend.model.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Transaction, UUID> {}
