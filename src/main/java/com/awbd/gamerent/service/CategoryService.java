package com.awbd.gamerent.service;

import com.awbd.gamerent.model.Category;
import com.awbd.gamerent.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public Category findCategoryById(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            return categoryOptional.get();
        } else {
            throw new RuntimeException("Categoria cu ID-ul " + id + " nu a fost gasita!");
        }
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}