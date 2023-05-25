package com.fintrack.backend.model.user;

import com.fintrack.backend.model.budget.Budget;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class User {
    String userId;
    Budget budget;
}
