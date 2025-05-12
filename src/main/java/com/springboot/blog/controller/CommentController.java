package com.springboot.blog.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.CommentDTO;
import com.springboot.blog.service.Commentservice;

import jakarta.validation.Valid;


// Controller For Comment Resources
@RestController // Marks this class as a RESTful web controller
@RequestMapping("/api/posts") // Base path for all endpoints related to posts and their comments
@Tag(
		name = "CRUD REST APIs for Comment Resources" // Swagger grouping for documentation
)
public class CommentController {

	@Autowired
	private Commentservice commentservice; // Injects the Commentservice to handle business logic

//	=================================================================================================================================

	// Create comment REST API
	@Operation(
			summary = "createComment REST API", // Short description for Swagger
			description = "Create new Comments to Post using Post Id" // Detailed Swagger description
	)
	@ApiResponse(
			responseCode = "201",
			description = "Http Status 201 CREATED" // Expected response status
	)
	@SecurityRequirement(name = "Bear Authentication") // JWT-based security requirement
	@PreAuthorize("hasRole('ADMIN')") // Only users with ADMIN role can create comments
	@PostMapping("/{postId}/comments") // POST request to create a comment under a specific post
	public ResponseEntity<CommentDTO> createComment(@PathVariable Long postId,
													@Valid @RequestBody CommentDTO commentDTO) {
		return new ResponseEntity<>(commentservice.createComment(postId, commentDTO), HttpStatus.CREATED); // Returns created comment with 201 status
	}

//	=================================================================================================================================

	// Get comments by postId REST API
	@Operation(
			summary = "getCommentsByPostId REST API", // Summary for Swagger UI
			description = "Fetch Comments from Database using Post Id" // Explains the endpoint
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS" // Status indicating successful retrieval
	)
	@GetMapping("/{postId}/comments") // GET request to fetch all comments for a specific post
	public List<CommentDTO> getCommentsByPostId(@PathVariable Long postId) {
		return commentservice.getCommentsByPostId(postId); // Returns list of comments
	}

//	=================================================================================================================================

	// Get comment by commentId REST API
	@Operation(
			summary = "getCommentsById REST API", // Swagger summary
			description = "Fetch Comments from Database using Post Id and Comment Id" // Description of logic
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS" // Status indicating success
	)
	@GetMapping("/{postId}/comments/{commentId}") // GET request for a specific comment under a specific post
	public ResponseEntity<CommentDTO> getCommentsById(@PathVariable Long postId, @PathVariable Long commentId) {
		return new ResponseEntity<>(commentservice.getCommentById(postId, commentId), HttpStatus.OK); // Return comment with 200 status
	}

//	=================================================================================================================================

	// Update comment REST API
	@Operation(
			summary = "updateComment REST API", // Summary for Swagger UI
			description = "Update Comments from Database using Post Id and Comment Id" // Explains functionality
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS" // Expected status
	)
	@SecurityRequirement(name = "Bear Authentication") // JWT security required
	@PreAuthorize("hasRole('ADMIN')") // Only ADMIN users can update comments
	@PutMapping("/{postId}/comments/{commentId}") // PUT request to update a specific comment
	public ResponseEntity<CommentDTO> updateComment(@PathVariable Long postId, @PathVariable Long commentId,
													@Valid @RequestBody CommentDTO commentDTO) {
		return new ResponseEntity<>(commentservice.updateComment(postId, commentId, commentDTO), HttpStatus.OK); // Returns updated comment
	}

//	=================================================================================================================================

	// Delete comment REST API
	@Operation(
			summary = "deleteComment REST API", // Swagger documentation summary
			description = "Delete Comments from Database using Post Id and Comment Id" // Description of endpoint
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS" // Indicates deletion success
	)
	@SecurityRequirement(name = "Bear Authentication") // JWT token required
	@PreAuthorize("hasRole('ADMIN')") // Only ADMIN role can delete comments
	@DeleteMapping("/{postId}/comments/{commentId}") // DELETE request to remove a specific comment
	public ResponseEntity<String> deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
		commentservice.deleteComment(postId, commentId); // Perform deletion
		return new ResponseEntity<>("Comment deleted with postId " + postId + " and commentId " + commentId,
				HttpStatus.OK); // Return confirmation message
	}
}
