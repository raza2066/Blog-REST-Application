package com.springboot.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
// Marks this exception with a BAD_REQUEST status code
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BlogApiException extends RuntimeException {

	private HttpStatus status;  // Holds the HTTP status code associated with the exception
	private String message;     // Holds the error message for the exception

//	=================================================================================================================================

	// Constructor to initialize status and message fields
	public BlogApiException(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}

	// Constructor to initialize with a custom message, status, and a second message
	public BlogApiException(String message, HttpStatus status, String message1) {
		super(message);   // Calls the superclass (RuntimeException) constructor
		this.status = status;
		this.message = message1;
	}

//	=================================================================================================================================

	// Getter for status field
	public HttpStatus getStatus() {
		return status;
	}

	// Override of getMessage method to return custom message
	@Override
	public String getMessage() {
		return message;
	}
}