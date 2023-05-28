package com.fintrack.backend.controller;

import com.fintrack.backend.model.transaction.Transaction;
import com.fintrack.backend.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController("/transactions")
@RequiredArgsConstructor
@Validated
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/{userId}")
    public Transaction createTransaction(@PathVariable("userId") UUID userId,
                                         @Valid @RequestBody Transaction transaction) {

    }
}
