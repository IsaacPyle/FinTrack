package com.fintrack.backend.model.budget;

import com.fintrack.backend.model.category.Category;
import com.fintrack.backend.model.transaction.Transaction;
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
import org.hibernate.Hibernate;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Builder
@Getter
@Setter
@ToString
@Entity
@Table(name = "budgetTable")
@NoArgsConstructor
@AllArgsConstructor
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID budgetId;

    @Column(name="transactions")
    List<Transaction> transactions;

    @Column(name="targetDate")
    Date targetDate;

    @Column(name="startDate")
    Date startDate;

    @Column(name="categories")
    List<Category> categories;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Budget budget = (Budget) o;
        return getBudgetId() != null && Objects.equals(getBudgetId(), budget.getBudgetId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
