package com.springboot.blog.service;

import com.springboot.blog.payload.CommentDTO;

import java.util.List;

//Service Interface for Comment table crud Methods
public interface Commentservice {
    //create new comment
    CommentDTO createComment (long postId, CommentDTO commentDTO);
    //fetch comment by post Id
    List<CommentDTO> getCommentsByPostId(long postId);
    //fetch comment by commentId
    CommentDTO getCommentById(long postId, long commentId);
    //update comment
    CommentDTO updateComment(long postId, long commentId, CommentDTO commentDTO);
    //delete comment
    void deleteComment(long postId, long commentId);
}
