package com.ecommerce.sb_ecom.controller;

import com.ecommerce.sb_ecom.config.AppConstants;
import com.ecommerce.sb_ecom.payload.CategoryDTO;
import com.ecommerce.sb_ecom.payload.CategoryResponse;
import com.ecommerce.sb_ecom.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CategoryController {
    private CategoryService categoryService;



    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/public/categories")
    public ResponseEntity<?> getAllCategories(
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String sort_by,
            @RequestParam(required = false) String sort_order) {

        try {
            CategoryResponse categoryResponse = categoryService.getAllCategories(pageNumber, pageSize, sort_by, sort_order);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Categories retrieved successfully");
            response.put("categories", categoryResponse.getContent());
            response.put("pageNumber", categoryResponse.getPageNumber());
            response.put("pageSize", categoryResponse.getPageSize());
            response.put("totalElements", categoryResponse.getTotalElements());
            response.put("totalPages", categoryResponse.getTotalPages());
            response.put("lastPage", categoryResponse.getLastPage());
            response.put("sortBy", categoryResponse.getSortBy());
            response.put("sortOrder", categoryResponse.getSortOrder());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/public/categories")
    public ResponseEntity<?> createCategory(@RequestBody CategoryDTO categoryDTO) {
        try {
            CategoryDTO savedCategory = categoryService.createCategory(categoryDTO);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Category created successfully");
            response.put("categoryId", savedCategory.getCategoryId());
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
    public ResponseEntity<?> updateCategory(@RequestBody CategoryDTO categoryDTO,
                                            @PathVariable Long categoryId) {
        try {
            CategoryDTO savedCategory = categoryService.updateCategory(categoryDTO, categoryId);
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