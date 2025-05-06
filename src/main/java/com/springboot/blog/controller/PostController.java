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

//Controller For Post Resources
@RestController
@RequestMapping("/api/posts")
@Tag(
		name = "CRUD REST APIs for Post Resources"
)
public class PostController {

	@Autowired
	private PostService postService;

	// Rest api Method to Create Post
	@Operation(
			summary = "createPost REST API",
			description = "Create New Posts in Database"
	)
	@ApiResponse(
			responseCode = "201",
			description = "Http Status 201 CREATED"
	)
	@SecurityRequirement(name = "Bear Authentication")
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO) {
		return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED);
	}

	// Rest api Method to Get all Posts
	// http://localhost:8080/api/posts?pageNo=<x>&pageSize=<y>&sortBy=<str>&sortDir=<asc
	// or desc>
	@Operation(
			summary = "getAllPosts REST API",
			description = "Retrieve all Posts from Database"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS"
	)
	@GetMapping
	public PostResponse getAllPosts(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRRECTION, required = false) String sortDir) {
		return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
	}

	// Rest api Method to Get Post by id
	@Operation(
			summary = "getPostsById REST API",
			description = "Retrieve Post from Database sing Post Id"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS"
	)
	@GetMapping("/{id}")
	public ResponseEntity<PostDTO> getPostsById(@PathVariable Long id) {
		return ResponseEntity.ok(postService.getPostById(id));
	}

	// Rest api Method to Update Post by id
	@Operation(
			summary = "updatePost REST API",
			description = "Update Post from Database using Post Id"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS"
	)
	@SecurityRequirement(name = "Bear Authentication")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO, @PathVariable Long id) {
		return ResponseEntity.ok(postService.updatePost(postDTO, id));
	}

	// Rest api Method to Delete Post by id
	@Operation(
			summary = "deletePost REST API",
			description = "Delete Post from Database using Post Id"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS"
	)
	@SecurityRequirement(name = "Bear Authentication")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePost(@PathVariable Long id) {
		postService.deletePost(id);
		return ResponseEntity.ok("Post with id " + id + " deleted successfully");
	}

	@Operation(
			summary = "getPostsByCategory REST API",
			description = "Get Post from Database using Category Id"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS"
	)
	@GetMapping("/category/{id}")
	public ResponseEntity<List<PostDTO>> getPostsByCategory(@PathVariable Long id){
		List<PostDTO> posts = postService.getPostsByCategoryId(id);
		return ResponseEntity.ok(posts);
	}
}
