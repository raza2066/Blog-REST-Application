package com.springboot.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.blog.entity.Post;

//no need for @Repository as jpa repository already has it
//Repository Layer extends jpa repository to automate db Crud operations
public interface PostRepository extends JpaRepository<Post, Long> {
    //Repository Layer extends jpa repository to automate db Crud operations
}
