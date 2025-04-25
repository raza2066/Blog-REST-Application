package com.springboot.blog.payload;

import lombok.Data;

//lombok annotation to reduce boilerplate code
@Data
public class PostDTO {
	//DTO Object for "post" to communicate with client
	private long id;
	private String title;
	private String description;
	private String content;

}
