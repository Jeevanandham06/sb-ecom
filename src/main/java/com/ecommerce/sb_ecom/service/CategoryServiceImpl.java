package com.ecommerce.sb_ecom.service;

import com.ecommerce.sb_ecom.exceptions.APIException;
import com.ecommerce.sb_ecom.exceptions.ResourceNotFoundException;
import com.ecommerce.sb_ecom.model.Category;
import com.ecommerce.sb_ecom.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        System.out.println("GET ALL - Fetching all categories");
        List<Category> categories = categoryRepository.findAll();

        if (categories.isEmpty()) {
            System.out.println("No categories found - throwing APIException");
            throw new APIException("No categories listed till now");
        }

        System.out.println("Found " + categories.size() + " categories");
        return categories;
    }

    @Override
    public Category createCategory(Category category) {
        System.out.println("CREATE - Creating new category: " + category.getCategoryName());

        Category existingCategory = categoryRepository.findBycategoryName(category.getCategoryName());

        if (existingCategory != null) {
            System.out.println("Category name already exists: " + category.getCategoryName());
            throw new APIException("Category with the name " + category.getCategoryName() + " already exists!");
        }

        Category savedCategory = categoryRepository.save(category);
        System.out.println("Category created with ID: " + savedCategory.getCategoryID());
        return savedCategory;
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
    public Category updateCategory(Category category, Long categoryId) {
        System.out.println("UPDATE - Checking category ID: " + categoryId);
        System.out.println("UPDATE - New category name: " + category.getCategoryName());

        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);

        if (optionalCategory.isPresent()) {
            Category existingCategory = optionalCategory.get();
            String oldName = existingCategory.getCategoryName();
            System.out.println("Category FOUND - ID: " + categoryId + ", Current Name: " + oldName);

            existingCategory.setCategoryName(category.getCategoryName());
            Category savedCategory = categoryRepository.save(existingCategory);

            System.out.println("Category UPDATED SUCCESSFULLY - ID: " + categoryId +
                    ", Old Name: " + oldName +
                    ", New Name: " + savedCategory.getCategoryName());
            return savedCategory;
        } else {
            System.out.println("Category NOT FOUND - ID: " + categoryId);
            System.out.println("THROWING ResourceNotFoundException");
            throw new ResourceNotFoundException("Category", "categoryId", categoryId);
        }
    }
}
