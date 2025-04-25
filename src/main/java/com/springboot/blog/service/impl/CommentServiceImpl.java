package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDTO;
import com.springboot.blog.payload.PostDTO;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.Commentservice;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

//Implementation class for CommentService Interface
@Service
public class CommentServiceImpl implements Commentservice {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;

    @Override
    public CommentDTO createComment(long postId, CommentDTO commentDTO) {
        Comment comment = DTOtoEntity(commentDTO);


        // get post by id from database, if not available throw resource not found exception
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        comment.setPost(post);
        return EntitytoDTO(commentRepository.save(comment));
    }

    @Override
    public List<CommentDTO> getCommentsByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment->EntitytoDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDTO getCommentById(long postId, long commentId) {
        // get post by id from database, if not available throw resource not found exception
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not Belong to the post");
        }

        return EntitytoDTO(comment);
    }

    @Override
    public CommentDTO updateComment(long postId, long commentId, CommentDTO commentDTO) {
        // get post by id from database, if not available throw resource not found exception
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not Belong to the post");
        }

        comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());
        comment.setBody(commentDTO.getBody());

        Comment updatedComment = commentRepository.save(comment);

        return EntitytoDTO(updatedComment);
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not Belong to the post");
        }

        commentRepository.delete(comment);
    }


    // Method to convert DTO to Entity
    private Comment DTOtoEntity(CommentDTO commentDTO) {
        Comment newComment = new Comment();
        newComment.setName(commentDTO.getName());
        newComment.setEmail(commentDTO.getEmail());
        newComment.setBody(commentDTO.getBody());
        return newComment;
    }

    // Method to convert Entity to DTO
    private CommentDTO EntitytoDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setName(comment.getName());
        commentDTO.setEmail(comment.getEmail());
        commentDTO.setBody(comment.getBody());
        return commentDTO;
    }
}
