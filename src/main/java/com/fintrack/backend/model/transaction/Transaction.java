package com.fintrack.backend.model.transaction;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class Transaction {
    String transactionId;
    String amount;
    Date transactionDate;
    TransactionType type;
    String description;
}
