package com.springboot.blog;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication

@OpenAPIDefinition(
		info = @Info(
				title = "Spring Boot Blog App Rest APISs",
				description = "A spring boot project that implements Rest APIs for a blog,having features for post,comment,category management." +
						" This project is secured with spring Security and JWT token.",
				version = "v1.0",
				contact = @Contact(
						name = "Nadeem Raza",
						email = "nadeemraza2066@gmail.com",
						url = "https://linkedin.com/in/nadeem-raza-103476282"
				),
				license = @License(
						name = "Apache License 2.0",
						url = "https://www.apache.org/licenses/LICENSE-2.0"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Spring Boot Blog Application Repository",
				url = "https://github.com/raza2066/Blog-REST-Application.git"
		)
)
public class BlogRestApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(BlogRestApplication.class, args);
	}

}
