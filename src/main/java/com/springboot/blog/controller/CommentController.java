package com.springboot.blog.controller;

import java.util.List;

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
@RequestMapping("/api/posts/")
public class CommentController {

	@Autowired
	private Commentservice commentservice;

	// Rest api Method to create comment
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/{postId}/comments")
	public ResponseEntity<CommentDTO> createComment(@PathVariable Long postId,
			@Valid @RequestBody CommentDTO commentDTO) {
		return new ResponseEntity<>(commentservice.createComment(postId, commentDTO), HttpStatus.CREATED);
	}

	// Rest api Method to get comments by postId
	@GetMapping("/{postId}/comments")
	public List<CommentDTO> getCommentsByPostId(@PathVariable Long postId) {
		return commentservice.getCommentsByPostId(postId);
	}

	// Rest api Method to get comment by commentId
	@GetMapping("/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDTO> getCommentsById(@PathVariable Long postId, @PathVariable Long commentId) {
		return new ResponseEntity<>(commentservice.getCommentById(postId, commentId), HttpStatus.OK);
	}

	// Rest api Method to update comment
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDTO> updateComment(@PathVariable Long postId, @PathVariable Long commentId,
			@Valid @RequestBody CommentDTO commentDTO) {
		return new ResponseEntity<>(commentservice.updateComment(postId, commentId, commentDTO), HttpStatus.OK);
	}

	// Rest api Method to delete comment

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{postId}/comments/{commentId}")
	public ResponseEntity<String> deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
		commentservice.deleteComment(postId, commentId);
		return new ResponseEntity<>("Comment deleted with postId " + postId + " and commentId " + commentId,
				HttpStatus.OK);
	}

}
