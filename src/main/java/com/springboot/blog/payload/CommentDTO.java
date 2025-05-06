package com.springboot.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
//lombok annotation to reduce boilerplate code
import lombok.Data;


@Schema(
		description = "CommentDTO model Information"
)
@Data
public class CommentDTO {
	// DTO Object for "comment" to communicate with client
	private Long id;

	// name should not be null or empty
	@NotEmpty(message = "Name should not be null or empty")
	private String name;

	// email should not be null or empty
	// email field validation
	@NotEmpty(message = "Email should not be null or empty")
	@Email
	private String email;

	// body should not be null or empty
	// body must contain minimum 10 characters
	@NotEmpty
	@Size(min = 10, message = "body must contain minimum 10 characters")
	private String body;
}
