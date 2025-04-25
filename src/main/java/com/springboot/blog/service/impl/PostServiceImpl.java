package com.springboot.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.springboot.blog.payload.PostResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDTO;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;


//Implementation class for PostService Interface
@Service
public class PostServiceImpl implements PostService {
	//Autowiring PostRepository to use jpa methods
	@Autowired
	private PostRepository postRepository;

	// Method to create post
	@Override
	public PostDTO createPost(PostDTO postDTO) {
		// convert DTO to entity
		Post post = DTOtoEntity(postDTO);
		// Saving post to database
		Post newPost = postRepository.save(post);
		// convert entity to DTO
		PostDTO newpostDTO = EntitytoDTO(newPost);
		return newpostDTO;

	}

	// Method to fetch all posts with pagination and sorting support
	@Override
	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.DESC.name())?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
		//creating pageable instance
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		//fetching posts from db
		Page<Post> posts = postRepository.findAll(pageable);
		//assigning page content in the list
		List<Post> listOfPost = posts.getContent();
		//returning list
		List<PostDTO> content = listOfPost.stream().map(post -> EntitytoDTO(post)).collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();
		postResponse.setContent(content);
		postResponse.setPageNo(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotalElements(posts.getTotalElements());
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setLast(posts.isLast());

		return postResponse;
	}

	// Method to fetch posts by id
	@Override
	public PostDTO getPostById(long id) {
		// get post by id from database, if not available throw resource not found exception
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		return EntitytoDTO(post);
	}

	// Method to Update post using id
	@Override
	public PostDTO updatePost(PostDTO postDTO, long id) {
		// get post by id from database, if not available throw resource not found exception
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		post.setTitle(postDTO.getTitle());
		post.setDescription(postDTO.getDescription());
		post.setContent(postDTO.getContent());
		Post newPost = postRepository.save(post);
		return EntitytoDTO(newPost);
	}

	// Method to delete post using id
	@Override
	public void deletePost(long id) {
		// get post by id from database, if not available throw resource not found exception
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		postRepository.delete(post);
	}

	// Method to convert DTO to Entity
	private Post DTOtoEntity(PostDTO postDTO) {
		Post newpost = new Post();
		newpost.setTitle(postDTO.getTitle());
		newpost.setDescription(postDTO.getDescription());
		newpost.setContent(postDTO.getContent());
		return newpost;
	}

	// Method to convert Entity to DTO
	private PostDTO EntitytoDTO(Post post) {
		PostDTO postDTO = new PostDTO();
		postDTO.setTitle(post.getTitle());
		postDTO.setDescription(post.getDescription());
		postDTO.setContent(post.getContent());
		postDTO.setId(post.getId());
		return postDTO;
	}
}
