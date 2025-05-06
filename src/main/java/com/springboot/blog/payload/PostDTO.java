package com.springboot.blog.payload;

import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

//lombok annotation to reduce boilerplate code
@Schema(
		description = "PostDTO model Information"
)
@Data
public class PostDTO {
	// DTO Object for "post" to communicate with client
	private Long id;

	// Title Should not be Empty or Null
	// Title should have atleast 2 characters
	@Schema(
			description = "Blog Post Title"
	)
	@NotEmpty
	@Size(min = 2, message = "Post title Should have at least 2 characters")
	private String title;

	// Description Should not be Empty or Null
	// Description should have atleast 10 characters
	@Schema(
			description = "Blog Post Description"
	)
	@NotEmpty
	@Size(min = 10, message = "Post Description Should have at least 10 characters")
	private String description;

	// Content should not be empty
	@Schema(
			description = "Blog Post Content"
	)
	@NotEmpty()
	private String content;

	@Schema(
			description = "Blog Post Comments"
	)
	private Set<CommentDTO> comments;

	@Schema(
			description = "Blog Post Category"
	)
	private Long categoryId;
}
