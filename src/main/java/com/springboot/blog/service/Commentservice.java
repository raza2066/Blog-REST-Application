package com.springboot.blog.service;

import com.springboot.blog.payload.CommentDTO;

import java.util.List;

//Service Interface for Comment table crud Methods
public interface Commentservice {

    CommentDTO createComment (long postId, CommentDTO commentDTO);

    List<CommentDTO> getCommentsByPostId(long postId);

    CommentDTO getCommentById(long postId, long commentId);

    CommentDTO updateComment(long postId, long commentId, CommentDTO commentDTO);

    void deleteComment(long postId, long commentId);
}
