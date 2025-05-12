package com.springboot.blog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data // Lombok generates getters, setters, toString, equals, and hashCode
@AllArgsConstructor // Lombok generates a constructor with all fields
@NoArgsConstructor // Lombok generates a no-argument constructor

@Entity // Marks this class as a JPA entity
@Table(name = "comments") // Maps to "comments" table in the database
public class Comment {

    @Id // Specifies primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
    private Long id;

    private String name;  // Name of the commenter
    private String email; // Email of the commenter
    private String body;  // Comment content

    @ManyToOne(fetch = FetchType.LAZY) // Many comments can belong to one post
    @JoinColumn(name = "post_id", nullable = false) // Foreign key column in the comments table
    private Post post; // Reference to the associated Post entity
}
