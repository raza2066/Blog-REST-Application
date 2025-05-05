package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.payload.PostDTO;
import com.springboot.blog.payload.PostResponse;

//Service Interface for Post table crud Methods
public interface PostService {
	//Create new Post
	PostDTO createPost(PostDTO postDTO);
	//Fetch all Posts
	PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
	//Fetch Post by id
	PostDTO getPostById(Long id);
	//Update Post by id
	PostDTO updatePost(PostDTO postDTO, Long id);
	//Delete Post by id
	void deletePost(Long id);
	List<PostDTO>  getPostsByCategoryId(Long id);
}
