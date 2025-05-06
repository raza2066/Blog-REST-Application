package com.springboot.blog.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

//Controller For Comment Resources
@RestController
@RequestMapping("/api/posts")
public class CommentController {

	@Autowired
	private Commentservice commentservice;

	// Rest api Method to create comment
	@Operation(
			summary = "createComment REST API",
			description = "Create new Comments to Post using Post Id"
	)
	@ApiResponse(
			responseCode = "201",
			description = "Http Status 201 CREATED"
	)
	@SecurityRequirement(name = "Bear Authentication")
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/{postId}/comments")
	public ResponseEntity<CommentDTO> createComment(@PathVariable Long postId,
			@Valid @RequestBody CommentDTO commentDTO) {
		return new ResponseEntity<>(commentservice.createComment(postId, commentDTO), HttpStatus.CREATED);
	}

	// Rest api Method to get comments by postId
	@Operation(
			summary = "getCommentsByPostId REST API",
			description = "Fetch Comments from Database using Post Id"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS"
	)
	@GetMapping("/{postId}/comments")
	public List<CommentDTO> getCommentsByPostId(@PathVariable Long postId) {
		return commentservice.getCommentsByPostId(postId);
	}

	// Rest api Method to get comment by commentId
	@Operation(
			summary = "getCommentsById REST API",
			description = "Fetch Comments from Database using Post Id and Comment Id"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS"
	)
	@GetMapping("/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDTO> getCommentsById(@PathVariable Long postId, @PathVariable Long commentId) {
		return new ResponseEntity<>(commentservice.getCommentById(postId, commentId), HttpStatus.OK);
	}

	// Rest api Method to update comment
	@Operation(
			summary = "updateComment REST API",
			description = "Update Comments from Database using Post Id and Comment Id"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS"
	)
	@SecurityRequirement(name = "Bear Authentication")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDTO> updateComment(@PathVariable Long postId, @PathVariable Long commentId,
			@Valid @RequestBody CommentDTO commentDTO) {
		return new ResponseEntity<>(commentservice.updateComment(postId, commentId, commentDTO), HttpStatus.OK);
	}

	// Rest api Method to delete comment
	@Operation(
			summary = "deleteComment REST API",
			description = "Delete Comments from Database using Post Id and Comment Id"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS"
	)
	@SecurityRequirement(name = "Bear Authentication")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{postId}/comments/{commentId}")
	public ResponseEntity<String> deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
		commentservice.deleteComment(postId, commentId);
		return new ResponseEntity<>("Comment deleted with postId " + postId + " and commentId " + commentId,
				HttpStatus.OK);
	}

}
