package com.springboot.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.DeleteExchange;

import com.springboot.blog.payload.PostDTO;
import com.springboot.blog.service.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	@Autowired
	private PostService postService;

	// Create Blog Post rest api
	@PostMapping
	public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {
		return new ResponseEntity<PostDTO>(postService.createpost(postDTO), HttpStatus.CREATED);
	}

	// Get all Posts rest api
	@GetMapping
	public List<PostDTO> getAllPosts() {
		return postService.getAllPosts();
	}

	// Get Post by id rest api
	@GetMapping("/{id}")
	public ResponseEntity<PostDTO> getPostsById(@PathVariable long id) {
		return ResponseEntity.ok(postService.getPostById(id));
	}

	// Update Post by id rest api
	@PutMapping("/{id}")
	public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable long id) {
		return ResponseEntity.ok(postService.updatePost(postDTO, id));
	}

	// Delete Post by id rest api
	@DeleteExchange("/{id}")
	public ResponseEntity<String> deletePost(@PathVariable long id) {
		postService.deletePost(id);
		return ResponseEntity.ok("Post with id " + id + " deleted successfully");
	}
}
