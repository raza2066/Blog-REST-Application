package com.springboot.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDTO;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;

	// Method to create post
	@Override
	public PostDTO createpost(PostDTO postDTO) {

		// convert DTO to entity
		Post post = DTOtoEntity(postDTO);

		// Saving post to database
		Post newPost = postRepository.save(post);

		// convert entity to DTO
		PostDTO postResponse = EntitytoDTO(newPost);

		return postResponse;

	}

	// Method to fetch all posts
	@Override
	public List<PostDTO> getAllPosts() {
		List<Post> posts = postRepository.findAll();
		return posts.stream().map(post -> EntitytoDTO(post)).collect(Collectors.toList());
	}

	// Method to fetch posts by id
	@Override
	public PostDTO getPostById(long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		return EntitytoDTO(post);
	}

	// Method to Update post using id
	@Override
	public PostDTO updatePost(PostDTO postDTO, long id) {
		// get post by id from database
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
		// get post by id from database
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		postRepository.delete(post);
	}

	// Method to convert DTO to Entity
	private Post DTOtoEntity(PostDTO postDTO) {
		Post newpost = new Post();

//		newpost.setTitle(postDTO.getTitle());
//		newpost.setDescription(postDTO.getDescription());
//		newpost.setContent(postDTO.getContent());

		BeanUtils.copyProperties(postDTO, newpost);
		return newpost;
	}

	// Method to convert Entity to DTO
	private PostDTO EntitytoDTO(Post post) {
		PostDTO postResponse = new PostDTO();

//		postResponse.setTitle(post.getTitle());
//		postResponse.setDescription(post.getDescription());
//		postResponse.setContent(post.getContent());
//		postResponse.setId(post.getId());

		BeanUtils.copyProperties(post, postResponse);
		return postResponse;
	}
}
