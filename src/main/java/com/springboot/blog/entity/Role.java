package com.springboot.blog.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter // Lombok generates getter methods for all fields
@Setter // Lombok generates setter methods for all fields
@NoArgsConstructor // Lombok generates a no-argument constructor
@AllArgsConstructor // Lombok generates a constructor with all fields

@Entity // Marks this class as a JPA entity, so it will be mapped to a table in the database
@Table(name = "roles") // Specifies the name of the table in the database, which is "roles"
public class Role {

	@Id // primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generates ID
	private Long id;


	private String name; // The name of the role (e.g., "ROLE_ADMIN", "ROLE_USER")
}
