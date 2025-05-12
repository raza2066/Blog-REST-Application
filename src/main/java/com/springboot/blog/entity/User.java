package com.springboot.blog.entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter // Generates getters for all fields
@Setter // Generates setters
@NoArgsConstructor // Generates no-args constructor
@AllArgsConstructor // Generates all-args constructor

@Entity // Marks this class as a JPA entity
@Table(name = "users") // Maps to the "users" table in the database
public class User {

	@Id // Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generated ID
	private Long id;

	private String name; // Name of the user

	@Column(nullable = false, unique = true) // Cannot be null and must be unique
	private String username;

	@Column(nullable = false, unique = true) // Cannot be null and must be unique
	private String email;

	@Column(nullable = false) // Cannot be null
	private String password; // Encrypted password stored here

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	// Many users can have many roles; roles are loaded immediately (EAGER)
	@JoinTable(
			name = "user_roles", // Join table name
			joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
			// Maps this entity's ID (user_id) in the join table
			inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
			// Maps the Role entity's ID (role_id) in the join table
	)
	private Set<Role> roles; // Set of roles associated with the user
}