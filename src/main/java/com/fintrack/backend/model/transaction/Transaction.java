package com.fintrack.backend.model.transaction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
    String amount;

    @Column(name = "transactionDate")
    Date transactionDate;

    @Column(name = "type")
    TransactionType type;

    @Column(name = "description")
    String description;

    @Column(name = "categoryId")
    String categoryId;
}
