package com.ecommerce.sb_ecom.service;

import com.ecommerce.sb_ecom.model.Category;
import com.ecommerce.sb_ecom.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;  // ✅ ONLY NEED THIS

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();  // ✅ CORRECT
    }

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);  // ✅ CORRECT
    }

    @Override
    public String deleteCategory(Long categoryId) {
        // ✅ FIXED: Use repository directly
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            categoryRepository.deleteById(categoryId);
            return "Category with categoryId: " + categoryId + " deleted successfully!";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category Not Found");
        }
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
        // ✅ FIXED: Use repository instead of ArrayList
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isPresent()) {
            Category existingCategory = optionalCategory.get();
            existingCategory.setCategoryName(category.getCategoryName());
            return categoryRepository.save(existingCategory);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category Not Found");
        }
    }
}