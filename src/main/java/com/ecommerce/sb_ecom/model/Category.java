package com.ecommerce.sb_ecom.model;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryID;

    private String categoryName;

    public Category() {
    }

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Long categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
