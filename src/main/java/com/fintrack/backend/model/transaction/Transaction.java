package com.fintrack.backend.model.transaction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Builder
@Getter
@Setter
@ToString
@Entity
@Table(name = "transactionTable")
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID transactionId;

    @Column(name = "amount")
    @NotBlank
    @Min(value = 0, message = "Transaction amount must not be negative!")
    BigDecimal amount;

    @Column(name = "transactionDate")
    @NotBlank
    Date transactionDate;

    @Column(name = "type")
    @NotBlank
    TransactionType type;

    @Column(name = "description")
    String description;

    @Column(name = "categoryId")
    @NotBlank
    String categoryId;
}
