package com.ecommerce.sb_ecom.service;

import com.ecommerce.sb_ecom.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private List<Category> categories=new ArrayList<>();
    private  Long nextId = 1L;
    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public Category createCategory(Category category) {
        category.setCategoryID(nextId++);
        categories.add(category);
        return category;

    }

    public  String deleteCategory(Long categoryId){
       Category category = categories.stream()
               .filter(c -> c.getCategoryID().equals(categoryId))
               .findFirst().orElseThrow(() ->
                       new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource Not Found"));


       categories.remove(category);
       return "category with categoryId:"+ categoryId+ "deleted Successfully !!";
    }

    @Override
    public Category upadteCategory(Category category, Long categoryId) {
        Optional<Category> optionalCategory =categories.stream()
                .filter(c -> c.getCategoryID().equals(categoryId))
                .findFirst();
        if (optionalCategory.isPresent()){
            Category existingCategory = optionalCategory.get();
            existingCategory.setCategoryName(category.getCategoryName());
            return existingCategory;
        }else{
            throw   new ResponseStatusException(HttpStatus.NOT_FOUND, "Category Not Found");
        }


    }

}
