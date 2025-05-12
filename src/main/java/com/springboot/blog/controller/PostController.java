package com.springboot.blog.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.PostDTO;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;

import jakarta.validation.Valid;

import java.util.List;

// Controller For Post Resources
@RestController // Marks this class as a REST controller
@RequestMapping("/api/posts") // Base URL mapping for all post-related APIs
@Tag(
		name = "CRUD REST APIs for Post Resources" // Swagger tag for grouping post APIs
)
public class PostController {

	@Autowired
	private PostService postService; // Injects PostService for business logic

//	=================================================================================================================================
	//Create Post REST API

	@Operation(
			summary = "createPost REST API", // Summary for Swagger documentation
			description = "Create New Posts in Database" // Description of this endpoint's purpose
	)
	@ApiResponse(
			responseCode = "201",
			description = "Http Status 201 CREATED" // Indicates successful creation
	)
	@SecurityRequirement(name = "Bear Authentication") // Requires JWT Bearer token
	@PreAuthorize("hasRole('ADMIN')") // Only users with ADMIN role can create posts
	@PostMapping // Handles POST requests to create a new post
	public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO) {
		return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED); // Returns created post with 201 status
	}

//	=================================================================================================================================
	//Get all Posts REST API

	// http://localhost:8080/api/posts?pageNo=<x>&pageSize=<y>&sortBy=<str>&sortDir=<asc or desc>
	@Operation(
			summary = "getAllPosts REST API", // Summary for Swagger
			description = "Retrieve all Posts from Database" // Describes what the endpoint does
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS" // Indicates successful data retrieval
	)
	@GetMapping // Handles GET requests to fetch paginated and sorted list of posts
	public PostResponse getAllPosts(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRRECTION, required = false) String sortDir) {
		return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir); // Delegates to service layer for fetching posts
	}

//	=================================================================================================================================
	//Create Post by id REST API
	@Operation(
			summary = "getPostsById REST API", // Summary for Swagger
			description = "Retrieve Post from Database sing Post Id" // Describes the function
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS" // Success response description
	)
	@GetMapping("/{id}") // Handles GET request to fetch a single post by ID
	public ResponseEntity<PostDTO> getPostsById(@PathVariable Long id) {
		return ResponseEntity.ok(postService.getPostById(id)); // Returns the post DTO wrapped in 200 OK
	}

//	=================================================================================================================================
	// Update Post REST API

	@Operation(
			summary = "updatePost REST API", // Swagger summary
			description = "Update Post from Database using Post Id" // Endpoint description
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS" // Status for successful update
	)
	@SecurityRequirement(name = "Bear Authentication") // JWT authentication required
	@PreAuthorize("hasRole('ADMIN')") // Only ADMIN can update posts
	@PutMapping("/{id}") // Handles PUT requests for updating a post by ID
	public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO, @PathVariable Long id) {
		return ResponseEntity.ok(postService.updatePost(postDTO, id)); // Returns updated post
	}

//	=================================================================================================================================
	//Delete Post REST API

	@Operation(
			summary = "deletePost REST API", // Swagger summary
			description = "Delete Post from Database using Post Id" // Description for Swagger
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS" // Indicates successful deletion
	)
	@SecurityRequirement(name = "Bear Authentication") // Requires authentication
	@PreAuthorize("hasRole('ADMIN')") // Only ADMIN can delete posts
	@DeleteMapping("/{id}") // Handles DELETE requests by post ID
	public ResponseEntity<String> deletePost(@PathVariable Long id) {
		postService.deletePost(id); // Deletes post by ID
		return ResponseEntity.ok("Post with id " + id + " deleted successfully"); // Confirmation message
	}

//	=================================================================================================================================
	//Get Posts by CategoryId REST API

	@Operation(
			summary = "getPostsByCategory REST API", // Summary for Swagger UI
			description = "Get Post from Database using Category Id" // Description for the endpoint
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS" // Indicates the fetch was successful
	)
	@GetMapping("/category/{id}") // Handles GET requests to fetch posts by category ID
	public ResponseEntity<List<PostDTO>> getPostsByCategory(@PathVariable Long id){
		List<PostDTO> posts = postService.getPostsByCategoryId(id); // Fetch posts by category
		return ResponseEntity.ok(posts); // Return post list with HTTP 200
	}
}
