package com.ecommerce.sb_ecom.service;

import com.ecommerce.sb_ecom.exceptions.APIException;
import com.ecommerce.sb_ecom.exceptions.ResourceNotFoundException;
import com.ecommerce.sb_ecom.model.Category;
import com.ecommerce.sb_ecom.payload.CategoryDTO;
import com.ecommerce.sb_ecom.payload.CategoryResponse;
import com.ecommerce.sb_ecom.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sort_by, String sort_order) {
        System.out.println("GET ALL - Fetching all categories");
        System.out.println("Sort by: " + sort_by + ", Order: " + sort_order);

        // First, check if ANY categories exist in database
        long totalCount = categoryRepository.count();
        System.out.println("Total categories in database: " + totalCount);

        if (totalCount == 0) {
            System.out.println("No categories found - throwing APIException");
            throw new APIException("No categories listed till now");
        }

        List<Category> categories;
        long totalElements;
        int totalPages;
        boolean lastPage;

        // Create sort object (with null checks)
        Sort sort = null;
        if (sort_by != null && !sort_by.trim().isEmpty()) {
            sort = sort_order != null && sort_order.equalsIgnoreCase("desc")
                    ? Sort.by(sort_by).descending()
                    : Sort.by(sort_by).ascending();
            System.out.println("Sorting by: " + sort_by + " in " +
                    (sort_order != null && sort_order.equalsIgnoreCase("desc") ? "DESC" : "ASC") + " order");
        }

        // Check if pagination parameters are provided
        if (pageNumber != null && pageSize != null) {
            System.out.println("Using pagination - Page: " + pageNumber + ", Size: " + pageSize);

            Pageable pageDetails;
            if (sort != null) {
                pageDetails = PageRequest.of(pageNumber, pageSize, sort);
            } else {
                pageDetails = PageRequest.of(pageNumber, pageSize);
            }

            Page<Category> categoryPage = categoryRepository.findAll(pageDetails);
            categories = categoryPage.getContent();
            totalElements = categoryPage.getTotalElements();
            totalPages = categoryPage.getTotalPages();
            lastPage = categoryPage.isLast();

            System.out.println("Page content size: " + categories.size());
        } else {
            System.out.println("No pagination parameters - fetching all records");

            // Get all categories with or without sorting
            if (sort != null) {
                categories = categoryRepository.findAll(sort);
            } else {
                categories = categoryRepository.findAll();
            }

            totalElements = categories.size();
            totalPages = 1;
            lastPage = true;
        }

        // Convert entities to DTOs using stream
        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        // Create response using Lombok - ONLY set CategoryResponse fields
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        categoryResponse.setPageNumber(pageNumber);
        categoryResponse.setPageSize(pageSize);
        categoryResponse.setTotalElements(totalElements);
        categoryResponse.setTotalPages(totalPages);
        categoryResponse.setLastPage(lastPage);
        categoryResponse.setSortBy(sort_by);
        categoryResponse.setSortOrder(sort_order);

        System.out.println("Returning " + categories.size() + " categories");
        return categoryResponse;
    }
    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        System.out.println("CREATE - Creating new category: " + categoryDTO.getCategoryName());

        // Check for duplicate category name
        Category existingCategory = categoryRepository.findByCategoryName(categoryDTO.getCategoryName());

        if (existingCategory != null) {
            System.out.println("Category name already exists: " + categoryDTO.getCategoryName());
            throw new APIException("Category with the name " + categoryDTO.getCategoryName() + " already exists!");
        }

        // Manual mapping - safer approach
        Category category = new Category();
        category.setCategoryName(categoryDTO.getCategoryName());

        // Save entity
        Category savedCategory = categoryRepository.save(category);
        System.out.println("Category created with ID: " + savedCategory.getCategoryId());

        // Convert saved Entity back to DTO
        return convertToDTO(savedCategory);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        System.out.println("DELETE - Checking category ID: " + categoryId);

        Optional<Category> category = categoryRepository.findById(categoryId);

        if (category.isPresent()) {
            System.out.println("Category FOUND - ID: " + categoryId + ", Name: " + category.get().getCategoryName());
            categoryRepository.deleteById(categoryId);
            System.out.println("Category DELETED successfully");
            return "Category with categoryId: " + categoryId + " deleted successfully!";
        } else {
            System.out.println("Category NOT FOUND - ID: " + categoryId);
            System.out.println("THROWING ResourceNotFoundException");
            throw new ResourceNotFoundException("Category", "categoryId", categoryId);
        }
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        System.out.println("UPDATE - Checking category ID: " + categoryId);
        System.out.println("UPDATE - New category name: " + categoryDTO.getCategoryName());

        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);

        if (optionalCategory.isPresent()) {
            Category existingCategory = optionalCategory.get();
            String oldName = existingCategory.getCategoryName();
            System.out.println("Category FOUND - ID: " + categoryId + ", Current Name: " + oldName);

            // Check if new name already exists (excluding current category)
            Category duplicateCategory = categoryRepository.findByCategoryName(categoryDTO.getCategoryName());
            if (duplicateCategory != null && !duplicateCategory.getCategoryId().equals(categoryId)) {
                System.out.println("Category name already exists: " + categoryDTO.getCategoryName());
                throw new APIException("Category with the name " + categoryDTO.getCategoryName() + " already exists!");
            }

            // Update entity
            existingCategory.setCategoryName(categoryDTO.getCategoryName());

            // Save updated entity
            Category savedCategory = categoryRepository.save(existingCategory);

            System.out.println("Category UPDATED SUCCESSFULLY - ID: " + categoryId +
                    ", Old Name: " + oldName +
                    ", New Name: " + savedCategory.getCategoryName());

            // Convert updated Entity back to DTO
            return convertToDTO(savedCategory);
        } else {
            System.out.println("Category NOT FOUND - ID: " + categoryId);
            System.out.println("THROWING ResourceNotFoundException");
            throw new ResourceNotFoundException("Category", "categoryId", categoryId);
        }
    }

    // Helper method to convert Entity to DTO
    private CategoryDTO convertToDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(category.getCategoryId());
        categoryDTO.setCategoryName(category.getCategoryName());
        return categoryDTO;
    }
}