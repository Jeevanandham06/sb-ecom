package com.ecommerce.sb_ecom.controller;

import com.ecommerce.sb_ecom.model.Category;
import com.ecommerce.sb_ecom.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/public/categories")
    public ResponseEntity<?> getAllCategories() {
        try {
            List<Category> allCategories = categoryService.getAllCategories();
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Categories retrieved successfully");
            response.put("count", allCategories.size());
            response.put("categories", allCategories);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/public/categories")
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        try {
            Category savedCategory = categoryService.createCategory(category);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Category created successfully");
            response.put("categoryId", savedCategory.getCategoryID());
            response.put("categoryName", savedCategory.getCategoryName());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable("categoryId") Long categoryId) {
        try {
            String status = categoryService.deleteCategory(categoryId);
            Map<String, Object> response = new HashMap<>();
            response.put("message", status);
            response.put("categoryId", categoryId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/public/categories/{categoryId}")
    public ResponseEntity<?> updateCategory(@RequestBody Category category,
                                            @PathVariable Long categoryId) {
        try {
            Category savedCategory = categoryService.updateCategory(category, categoryId);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Category updated successfully");
            response.put("categoryId", categoryId);
            response.put("categoryName", savedCategory.getCategoryName());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}