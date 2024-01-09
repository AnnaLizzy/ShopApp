package com.example.shopapp.services;

import com.example.shopapp.dtos.CategoryDTO;
import com.example.shopapp.models.Category;

import java.util.List;

public interface ICategoryServices {
    Category createCategory(CategoryDTO category);
    Category getCategoryById (long id);
    List<Category> getAllCategories();
    Category updateCategory(long categoryId,CategoryDTO category);
    void deleteCategory(long id);
}
