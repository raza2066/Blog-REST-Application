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
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper modelMapper;

    // Method to create comment in a given post using postId
    @Override
    public CommentDTO createComment(Long postId, CommentDTO commentDTO) {
        Comment comment = DTOtoEntity(commentDTO);
        // get post by id from database, if not available throw resource not found
        // exception
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        comment.setPost(post);
        return EntitytoDTO(commentRepository.save(comment));
    }

    // Method to fetch comment for a given post using postId
    @Override
    public List<CommentDTO> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> EntitytoDTO(comment)).collect(Collectors.toList());
    }

    // Method to fetch comment by commentId
    @Override
    public CommentDTO getCommentById(Long postId, Long commentId) {
        Comment comment = retrieveComment(postId, commentId);
        return EntitytoDTO(comment);
    }

    // Method to update comment
    @Override
    public CommentDTO updateComment(Long postId, Long commentId, CommentDTO commentDTO) {
        Comment comment = retrieveComment(postId, commentId);
        comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());
        comment.setBody(commentDTO.getBody());

        Comment updatedComment = commentRepository.save(comment);

        return EntitytoDTO(updatedComment);
    }

    // method to delete comment
    @Override
    public void deleteComment(Long postId, Long commentId) {
        commentRepository.delete(retrieveComment(postId, commentId));
    }

    // method to check whether the respective post and comments are present in the
    // database and return the comment
    private Comment retrieveComment(Long postId, Long commentId) {
        // get post by id from database, if not available throw resource not found
        // exception
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        // get comment by id from database, if not available throw resource not found
        // exception
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        // Check if comment beLong to respective post,If not throw Bad Request Exception
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not BeLong to the post");
        }
        return comment;
    }

    // Method to convert DTO to Entity
    private Comment DTOtoEntity(CommentDTO commentDTO) {
        // copying properties
        Comment newComment = modelMapper.map(commentDTO, Comment.class);
//		newComment.setName(commentDTO.getName());
//		newComment.setEmail(commentDTO.getEmail());
//		newComment.setBody(commentDTO.getBody());
        return newComment;
    }

    // Method to convert Entity to DTO
    private CommentDTO EntitytoDTO(Comment comment) {
        // copying properties
        CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);
//		commentDTO.setId(comment.getId());
//		commentDTO.setName(comment.getName());
//		commentDTO.setEmail(comment.getEmail());
//		commentDTO.setBody(comment.getBody());
        return commentDTO;
    }
}
