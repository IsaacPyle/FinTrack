package com.fintrack.backend.model.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
public class Category {

    UUID categoryId;

    String name;

    String description;
}
