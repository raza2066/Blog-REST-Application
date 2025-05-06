package com.springboot.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;


@Schema(
		description = "ErrorDetails model Information"
)
public class ErrorDetails {
	private Date timestamp;
	private String message;
	private String details;

	public ErrorDetails(Date timestamp, String message, String details) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}

}
