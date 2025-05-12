package com.springboot.blog.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter // Lombok generates getters for all fields
@Setter // Lombok generates setters for all fields
@AllArgsConstructor // Lombok generates a constructor with all fields
@NoArgsConstructor  // Lombok generates a no-argument constructor

@Entity // Marks this class as a JPA entity
@Table(
		name = "posts", // Maps this entity to the "posts" table
		uniqueConstraints = { @UniqueConstraint(columnNames = { "title" }) } // Enforces unique titles
)
public class Post {

	@Id // Specifies the primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generates the ID (auto-increment)
	private Long id;

	@Column(name = "title", nullable = false) // "title" column, must not be null
	private String title;

	@Column(name = "description", nullable = false) // "description" column, must not be null
	private String description;

	@Column(name = "content", nullable = false) // "content" column, must not be null
	private String content;

	@OneToMany(
			mappedBy = "post", // Refers to the "post" field in Comment entity
			cascade = CascadeType.ALL, // Cascade all operations (persist, remove, etc.)
			orphanRemoval = true // Delete comments if they are removed from this post
	)
	private Set<Comment> comments = new HashSet<>(); // A Post can have many Comments

	@ManyToOne(fetch = FetchType.LAZY) // Many Posts can belong to one Category
	@JoinColumn(name = "category_id") // Foreign key in the "posts" table
	private Category category; // The associated Category
}