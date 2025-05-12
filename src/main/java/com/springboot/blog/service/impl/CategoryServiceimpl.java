package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Category;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CategoryDTO;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

//Implementation class for CategoryService Interface
@Service
public class CategoryServiceimpl implements CategoryService {

    private CategoryRepository categoryRepository;  // Repository for Category entity
    private ModelMapper modelMapper;  // ModelMapper for DTO and entity conversions

    @Autowired
    public CategoryServiceimpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    // =================================================================================================================================

    // Method to add a new category
    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDTO) {
        // Convert CategoryDTO to Category entity
        Category category = modelMapper.map(categoryDTO, Category.class);

        // Save the new category to the database
        Category newcategory = categoryRepository.save(category);

        // Convert saved entity back to DTO and return
        return modelMapper.map(newcategory, CategoryDTO.class);
    }

    // =================================================================================================================================

    // Method to fetch a category by its ID
    @Override
    public CategoryDTO getCategory(Long id) {
        // Retrieve category from database by ID, throw exception if not found
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Id", id));

        // Convert the Category entity to DTO and return
        return modelMapper.map(category, CategoryDTO.class);
    }

    // =================================================================================================================================

    // Method to fetch all categories
    @Override
    public List<CategoryDTO> getAllCategories() {
        // Fetch all categories from the database
        List<Category> categories = categoryRepository.findAll();

        // Convert list of Category entities to list of CategoryDTOs
        return categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
    }

    // =================================================================================================================================

    // Method to update an existing category
    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long id) {
        // Retrieve the category from the database by ID, throw exception if not found
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Id", id));

        // Update the category with new data from DTO
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());

        // Save the updated category and convert the entity to DTO
        return modelMapper.map(categoryRepository.save(category), CategoryDTO.class);
    }

    // =================================================================================================================================

    // Method to delete a category by ID
    @Override
    public void deleteCategory(Long id) {
        // Retrieve the category from the database by ID, throw exception if not found
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Id", id));

        // Delete the retrieved category
        categoryRepository.delete(category);
    }
}

