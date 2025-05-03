package com.springboot.blog.controller;

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

//Controller For Post Resources
@RestController
@RequestMapping("/api/posts")
public class PostController {

	@Autowired
	private PostService postService;

	// Rest api Method to Create Post
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO) {
		return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED);
	}

	// Rest api Method to Get all Posts
	// http://localhost:8080/api/posts?pageNo=<x>&pageSize=<y>&sortBy=<str>&sortDir=<asc
	// or desc>
	@GetMapping
	public PostResponse getAllPosts(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRRECTION, required = false) String sortDir) {
		return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
	}

	// Rest api Method to Get Post by id
	@GetMapping("/{id}")
	public ResponseEntity<PostDTO> getPostsById(@PathVariable long id) {
		return ResponseEntity.ok(postService.getPostById(id));
	}

	// Rest api Method to Update Post by id
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO, @PathVariable long id) {
		return ResponseEntity.ok(postService.updatePost(postDTO, id));
	}

	// Rest api Method to Delete Post by id
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePost(@PathVariable long id) {
		postService.deletePost(id);
		return ResponseEntity.ok("Post with id " + id + " deleted successfully");
	}
}
