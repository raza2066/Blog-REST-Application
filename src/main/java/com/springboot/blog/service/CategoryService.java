package com.springboot.blog.service;

import com.springboot.blog.payload.CategoryDTO;

import java.util.List;

public interface CategoryService {
    //create new category
    CategoryDTO addCategory(CategoryDTO categoryDTO);
    //get gategory by id
    CategoryDTO getCategory(Long id);
    //get all categories
    List<CategoryDTO> getAllCategories();
    //update category
    CategoryDTO updateCategory(CategoryDTO categoryDTO,Long id);
    //delete category
    void deleteCategory(Long id);
}
