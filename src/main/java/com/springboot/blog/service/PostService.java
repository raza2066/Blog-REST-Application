package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.payload.PostDTO;

public interface PostService {

	PostDTO createpost(PostDTO postDTO);

	List<PostDTO> getAllPosts();

	PostDTO getPostById(long id);

	PostDTO updatePost(PostDTO postDTO, long id);

	void deletePost(long id);
}
