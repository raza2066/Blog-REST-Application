package com.springboot.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.blog.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    //Repository Layer extends jpa repository to automate db Crud operations
}
