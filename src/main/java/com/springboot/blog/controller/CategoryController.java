package com.springboot.blog.controller;

import com.springboot.blog.payload.CategoryDTO;
import com.springboot.blog.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


// Controller For Category Resources
@RestController // Marks this class as a Spring REST controller
@RequestMapping("/api/categories") // Base URL path for all category-related endpoints
@Tag(
        name = "CRUD REST APIs for Category Resources" // Swagger tag for organizing category APIs
)
public class CategoryController {

    private CategoryService categoryService; // Service layer for category operations

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService; // Constructor-based injection of CategoryService
    }

//    =================================================================================================================================
    //Create Category REST API

    @Operation(
            summary = "createCategory REST API", // Brief summary for Swagger documentation
            description = "Create new category in Database" // Detailed description for Swagger
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED" // Indicates resource creation was successful
    )
    @SecurityRequirement(name = "Bear Authentication") // Requires JWT authentication
    @PreAuthorize("hasRole('ADMIN')") // Only users with ADMIN role can access this endpoint
    @PostMapping // Handles HTTP POST requests
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO){
        return new ResponseEntity<>(categoryService.addCategory(categoryDTO), HttpStatus.CREATED); // Creates category and returns 201
    }

//    =================================================================================================================================
    //Get Category by id REST API

    @Operation(
            summary = "getCategory REST API", // Swagger summary
            description = "Fetch Category from Database using Category Id" // Description for Swagger
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS" // Indicates successful retrieval
    )
    @GetMapping("/{id}") // Handles GET requests with path variable for category ID
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.getCategory(id)); // Returns category with HTTP 200
    }

//    =================================================================================================================================
    //Get all Category REST API

    @Operation(
            summary = "getAllCategories REST API", // Summary for Swagger
            description = "Fetch all categories from Database" // Description of the endpoint
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS" // Indicates successful fetch
    )
    @GetMapping // Handles GET requests without path variable
    public ResponseEntity<List<CategoryDTO>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories()); // Returns all categories
    }

//    =================================================================================================================================
    //Update Category REST API

    @Operation(
            summary = "updateCategory REST API", // Summary for Swagger
            description = "Update Category from Database using Category Id" // Description for documentation
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS" // Indicates update was successful
    )
    @SecurityRequirement(name = "Bear Authentication") // JWT token is required
    @PreAuthorize("hasRole('ADMIN')") // Only ADMINs can update categories
    @PutMapping("/{id}") // Handles PUT requests for updating category by ID
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable Long id){
        return ResponseEntity.ok(categoryService.updateCategory(categoryDTO, id)); // Updates category and returns response
    }

//    =================================================================================================================================
    //Delete Category REST API

    @Operation(
            summary = "deleteCategory REST API", // Swagger summary
            description = "Delete Category from Database using Category Id" // Description for documentation
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS" // Indicates deletion was successful
    )
    @SecurityRequirement(name = "Bear Authentication") // JWT token required
    @PreAuthorize("hasRole('ADMIN')") // Only ADMINs can delete categories
    @DeleteMapping("/{id}") // Handles DELETE requests by category ID
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id); // Deletes the category with specified ID
        return ResponseEntity.ok("Category deleted with id: "+id); // Returns confirmation message
    }
}
