package com.springboot.blog.payload;

//lombok annotation to reduce boilerplate code
import lombok.Data;

@Data

public class CommentDTO {
    //DTO Object for "comment" to communicate with client
    private long id;
    private String name;
    private String email;
    private String body;
}
