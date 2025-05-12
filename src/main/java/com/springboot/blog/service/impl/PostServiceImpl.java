package com.springboot.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.springboot.blog.entity.Category;
import com.springboot.blog.repository.CategoryRepository;
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
	private PostRepository postRepository;  // Repository for Post entity

	@Autowired
	private ModelMapper modelMapper;  // ModelMapper for converting between DTO and entity

	@Autowired
	CategoryRepository categoryRepository;  // Repository for Category entity

//	=================================================================================================================================

	// Method to create post
	@Override
	public PostDTO createPost(PostDTO postDTO) {
		// Find the category by its ID, if not found throw ResourceNotFoundException
		Category category = categoryRepository.findById(postDTO.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", postDTO.getCategoryId()));

		// Convert the input DTO to entity and set the category
		Post post = mapToEntity(postDTO);
		post.setCategory(category);

		// Save the new post to the repository and convert the saved entity back to DTO
		Post newPost = postRepository.save(post);
		PostDTO newpostDTO = mapToDTO(newPost);
		return newpostDTO;
	}

//	=================================================================================================================================

	// Method to fetch all posts with pagination and sorting support
	@Override
	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
		// Determine sorting direction (ascending or descending)
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.DESC.name())
				? Sort.by(sortBy).descending()
				: Sort.by(sortBy).ascending();

		// Create pageable instance with page number, size, and sorting
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

		// Fetch posts from the database with the pageable parameter
		Page<Post> posts = postRepository.findAll(pageable);

		// Extract the list of posts from the Page object
		List<Post> listOfPost = posts.getContent();

		// Convert the list of Post entities to PostDTO objects
		List<PostDTO> content = listOfPost.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

		// Construct the response DTO with pagination information
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(content);
		postResponse.setPageNo(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotalElements(posts.getTotalElements());
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setLast(posts.isLast());

		return postResponse;
	}

//	=================================================================================================================================

	// Method to fetch post by ID
	@Override
	public PostDTO getPostById(Long id) {
		// Retrieve post by ID and throw exception if not found
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

		// Convert the post entity to DTO and return
		return mapToDTO(post);
	}

//	=================================================================================================================================

	// Method to update an existing post using its ID
	@Override
	public PostDTO updatePost(PostDTO postDTO, Long id) {
		// Retrieve the post by ID, throw exception if not found
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

		// Retrieve category by ID and throw exception if not found
		Category category = categoryRepository.findById(postDTO.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", postDTO.getCategoryId()));

		// Update the post entity with new values from the DTO
		post.setTitle(postDTO.getTitle());
		post.setDescription(postDTO.getDescription());
		post.setContent(postDTO.getContent());
		post.setCategory(category);

		// Save the updated post to the repository
		Post newPost = postRepository.save(post);

		// Convert the updated post entity back to DTO and return
		return mapToDTO(newPost);
	}

//	=================================================================================================================================

	// Method to delete a post by its ID
	@Override
	public void deletePost(Long id) {
		// Retrieve the post by ID, throw exception if not found
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

		// Delete the post from the repository
		postRepository.delete(post);
	}

//	=================================================================================================================================

	// Method to fetch posts by category ID
	@Override
	public List<PostDTO> getPostsByCategoryId(Long id) {
		// Retrieve the category by ID and throw exception if not found
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", id));

		// Retrieve posts associated with the category
		List<Post> posts = postRepository.findByCategoryId(id);

		// Convert the list of posts to PostDTO and return
		return posts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
	}

//	=================================================================================================================================

	// Method to convert PostDTO to Post entity
	private Post mapToEntity(PostDTO postDTO) {
		// Convert PostDTO to Post entity using ModelMapper
		Post newpost = modelMapper.map(postDTO, Post.class);
		return newpost;
	}

//	=================================================================================================================================

	// Method to convert Post entity to PostDTO
	private PostDTO mapToDTO(Post post) {
		// Convert Post entity to PostDTO using ModelMapper
		PostDTO postDTO = modelMapper.map(post, PostDTO.class);
		return postDTO;
	}
}
