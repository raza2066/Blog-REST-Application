package com.springboot.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDTO;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.Commentservice;

//Implementation class for CommentService Interface
@Service
public class CommentServiceImpl implements Commentservice {

	@Autowired
	private CommentRepository commentRepository;  // Repository for Comment entity

	@Autowired
	private PostRepository postRepository;  // Repository for Post entity

	@Autowired
	private ModelMapper modelMapper;  // ModelMapper for DTO and entity conversions

//	=================================================================================================================================

	// Method to create comment in a given post using postId
	@Override
	public CommentDTO createComment(Long postId, CommentDTO commentDTO) {
		// Convert the CommentDTO to Comment entity
		Comment comment = mapToEntity(commentDTO);

		// Fetch post by id from database, if not found throw ResourceNotFoundException
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		// Associate the comment with the post
		comment.setPost(post);

		// Save the comment to the database and convert the saved entity to DTO
		return mapToDTO(commentRepository.save(comment));
	}

//	=================================================================================================================================

	// Method to fetch all comments for a given post using postId
	@Override
	public List<CommentDTO> getCommentsByPostId(Long postId) {
		// Fetch all comments for the given postId
		List<Comment> comments = commentRepository.findByPostId(postId);

		// Convert list of Comment entities to list of CommentDTO
		return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
	}

//	=================================================================================================================================

	// Method to fetch a specific comment by commentId for a given post
	@Override
	public CommentDTO getCommentById(Long postId, Long commentId) {
		// Retrieve the comment using the postId and commentId
		Comment comment = retrieveComment(postId, commentId);
		return mapToDTO(comment);  // Convert the comment entity to DTO
	}

//	=================================================================================================================================

	// Method to update an existing comment
	@Override
	public CommentDTO updateComment(Long postId, Long commentId, CommentDTO commentDTO) {
		// Retrieve the comment for update
		Comment comment = retrieveComment(postId, commentId);

		// Update the comment properties with new data from the DTO
		comment.setName(commentDTO.getName());
		comment.setEmail(commentDTO.getEmail());
		comment.setBody(commentDTO.getBody());

		// Save the updated comment and convert the entity to DTO
		Comment updatedComment = commentRepository.save(comment);
		return mapToDTO(updatedComment);
	}

//	=================================================================================================================================

	// Method to delete a comment
	@Override
	public void deleteComment(Long postId, Long commentId) {
		// Delete the comment by retrieving it first
		commentRepository.delete(retrieveComment(postId, commentId));
	}

//	=================================================================================================================================

	// Method to check whether the respective post and comments are present in the database and return the comment
	private Comment retrieveComment(Long postId, Long commentId) {
		// Fetch the post by ID, throw exception if not found
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		// Fetch the comment by ID, throw exception if not found
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		// Ensure the comment belongs to the respective post, otherwise throw a BadRequestException
		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to the post");
		}

		return comment;  // Return the comment
	}

//	=================================================================================================================================

	// Method to convert CommentDTO to Comment entity
	private Comment mapToEntity(CommentDTO commentDTO) {
		// Convert the CommentDTO to Comment entity using ModelMapper
		Comment newComment = modelMapper.map(commentDTO, Comment.class);
		return newComment;
	}

//	=================================================================================================================================

	// Method to convert Comment entity to CommentDTO
	private CommentDTO mapToDTO(Comment comment) {
		// Convert the Comment entity to CommentDTO using ModelMapper
		CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);
		return commentDTO;
	}
}