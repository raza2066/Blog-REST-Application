package com.springboot.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BlogApiException extends RuntimeException {

	private HttpStatus status;
	private String message;

	public BlogApiException(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}

	public BlogApiException(String message, HttpStatus status, String message1) {
		super(message);
		this.status = status;
		this.message = message1;
	}

	public HttpStatus getStatus() {
		return status;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
