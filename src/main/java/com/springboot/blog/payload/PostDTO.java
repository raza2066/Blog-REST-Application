package com.springboot.blog.payload;

import java.util.Set;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

//lombok annotation to reduce boilerplate code
@Data
public class PostDTO {
	// DTO Object for "post" to communicate with client
	private long id;

	// Title Should not be Empty or Null
	// Title should have atleast 2 characters
	@NotEmpty
	@Size(min = 2, message = "Post title Should have at least 2 characters")
	private String title;

	// Description Should not be Empty or Null
	// Description should have atleast 10 characters
	@NotEmpty
	@Size(min = 10, message = "Post Description Should have at least 10 characters")
	private String description;

	// Content should not be empty
	@NotEmpty()
	private String content;
	private Set<CommentDTO> comments;
}
