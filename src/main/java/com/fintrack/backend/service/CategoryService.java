package com.fintrack.backend.service;

import com.fintrack.backend.model.category.Category;
import com.fintrack.backend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Optional<Category> getCategoryById(UUID categoryId) {
        return categoryRepository.findById(categoryId);
    }

    public Optional<Category> getCategoryByName(String name) {
        return categoryRepository.findAll()
            .stream()
            .filter(cat -> cat.getName().equals(name))
            .findFirst();
    }

    public Category putCategory(String name) {
        return getCategoryByName(name)
            .orElse(categoryRepository.save(Category.builder()
                .name(name)
                .build()));
    }
}
