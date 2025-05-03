package com.springboot.blog.repository;

import com.springboot.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//Repository Layer extends jpa repository to automate db Crud operations
public interface CommentRepository extends JpaRepository<Comment, Long> {
    //method to find comment by postId
    List<Comment> findByPostId(Long postId);
}
