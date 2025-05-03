package com.springboot.blog.service;

import com.springboot.blog.payload.CommentDTO;

import java.util.List;

//Service Interface for Comment table crud Methods
public interface Commentservice {
    //create new comment
    CommentDTO createComment(Long postId, CommentDTO commentDTO);

    //fetch comment by post Id
    List<CommentDTO> getCommentsByPostId(Long postId);

    //fetch comment by commentId
    CommentDTO getCommentById(Long postId, Long commentId);

    //update comment
    CommentDTO updateComment(Long postId, Long commentId, CommentDTO commentDTO);

    //delete comment
    void deleteComment(Long postId, Long commentId);
}
