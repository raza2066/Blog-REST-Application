package com.springboot.blog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter // Lombok generates getter methods for all fields
@Setter // Lombok generates setter methods for all fields
@NoArgsConstructor // Lombok generates a no-argument constructor
@AllArgsConstructor // Lombok generates a constructor with all arguments

@Entity // Marks this class as a JPA entity
@Table(name = "categories") // Maps this entity to the "categories" table in the DB
public class Category {

    @Id // Primary key annotation
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremented ID
    private Long id;

    private String name; // Column to store category name
    private String description; // Column to store category description

    @OneToMany(
            mappedBy =  "category", // Refers to the 'category' field in the Post entity
            cascade = CascadeType.ALL, // All cascade operations like persist, merge, remove, etc.
            orphanRemoval = true // If a post is removed from the list, delete it from DB too
    )
    private List<Post> posts; // One category can have many posts
}