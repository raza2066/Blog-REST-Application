package com.springboot.blog.payload;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtAthResponse {
    private String accessToken;
    private String tokenType = "Bearer";

}
