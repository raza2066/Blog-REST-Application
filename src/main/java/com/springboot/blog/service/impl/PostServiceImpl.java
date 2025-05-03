package com.springboot.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDTO;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;

//Implementation class for PostService Interface
@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;
	@Autowired
	private ModelMapper modelMapper;

	// Method to create post
	@Override
	public PostDTO createPost(PostDTO postDTO) {
		Post post = DTOtoEntity(postDTO); // convert input DTO to entity
		Post newPost = postRepository.save(post); // Saving post to database
		PostDTO newpostDTO = EntitytoDTO(newPost); // convert returned entity to DTO
		return newpostDTO;
	}

	// Method to fetch all posts with pagination and sorting support
	@Override
	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
		// creating sort parameter for pageable
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortBy).descending()
				: Sort.by(sortBy).ascending();
		// creating pageable instance
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		// fetching posts from db
		Page<Post> posts = postRepository.findAll(pageable);
		// assigning page content to a list
		List<Post> listOfPost = posts.getContent();
		// converting list of Post to List of PostDTO
		List<PostDTO> content = listOfPost.stream().map(post -> EntitytoDTO(post)).collect(Collectors.toList());

		// creating response DTO
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
		// get post by id from database, if not available throw resource not found
		// exception
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		return EntitytoDTO(post);
	}

	// Method to Update post using id
	@Override
	public PostDTO updatePost(PostDTO postDTO, long id) {
		// get post by id from database, if not available throw resource not found
		// exception
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		// update entity to save changes
		post.setTitle(postDTO.getTitle());
		post.setDescription(postDTO.getDescription());
		post.setContent(postDTO.getContent());
		Post newPost = postRepository.save(post);
		return EntitytoDTO(newPost);
	}

	// Method to delete post using id
	@Override
	public void deletePost(long id) {
		// get post by id from database, if not available throw resource not found
		// exception
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		postRepository.delete(post);
	}

	// Method to convert DTO to Entity
	private Post DTOtoEntity(PostDTO postDTO) {
		// copying properties
		Post newpost = modelMapper.map(postDTO, Post.class);
//		newpost.setTitle(postDTO.getTitle());
//		newpost.setDescription(postDTO.getDescription());
//		newpost.setContent(postDTO.getContent());
		return newpost;
	}

	// Method to convert Entity to DTO
	private PostDTO EntitytoDTO(Post post) {
		// copying properties
		PostDTO postDTO = modelMapper.map(post, PostDTO.class);
//		postDTO.setTitle(post.getTitle());
//		postDTO.setDescription(post.getDescription());
//		postDTO.setContent(post.getContent());
//		postDTO.setId(post.getId());
		return postDTO;
	}
}
