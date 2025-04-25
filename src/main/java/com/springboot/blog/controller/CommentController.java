package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDTO;
import com.springboot.blog.service.Commentservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/")
public class CommentController {

    @Autowired
    private Commentservice commentservice;

    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable long postId, @RequestBody CommentDTO commentDTO){
        return new ResponseEntity<>(commentservice.createComment(postId , commentDTO), HttpStatus.CREATED);
    }
    @GetMapping("/{postId}/comments")
    public List<CommentDTO> getCommentsByPostId(@PathVariable long postId){
        return commentservice.getCommentsByPostId(postId);
    }

    @GetMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> getCommentsById(@PathVariable long postId, @PathVariable long commentId){
        return new ResponseEntity<>(commentservice.getCommentById(postId,commentId),HttpStatus.OK);
    }

    @PutMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(
            @PathVariable  long postId,
            @PathVariable  long commentId,
            @RequestBody CommentDTO commentDTO
    ){
        return new ResponseEntity<>(commentservice.updateComment(postId,commentId,commentDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable long postId, @PathVariable long commentId){
        commentservice.deleteComment(postId,commentId);
        return new ResponseEntity<>("Comment deleted with postId "+postId+" and commentId "+commentId,HttpStatus.OK);
    }

}
