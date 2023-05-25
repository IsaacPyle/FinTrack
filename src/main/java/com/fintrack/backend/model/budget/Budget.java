package com.fintrack.backend.model.budget;

import com.fintrack.backend.model.transaction.Transaction;

import java.util.Date;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Budget {
    String budgetId;
    List<Transaction> transactions;
    Date targetDate;
    Date startDate;
}
