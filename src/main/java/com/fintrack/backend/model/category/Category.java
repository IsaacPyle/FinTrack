package com.fintrack.backend.model.category;

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

import java.util.UUID;

@Builder
@Getter
@Setter
@ToString
@Entity
@Table(name = "budgetTable")
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID categoryId;

    @Column(name = "name")
    String name;

    @Column(name = "description")
    String description;

    @Column(name = "amount")
    int amount;
}
