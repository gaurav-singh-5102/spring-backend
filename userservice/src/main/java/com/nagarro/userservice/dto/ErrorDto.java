package com.nagarro.userservice.dto;

import java.time.LocalDateTime;

public class ErrorDto {

	private String error;
	private LocalDateTime timestamp;

	public ErrorDto(String error) {
		this.error = error;
		this.timestamp = LocalDateTime.now();
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
}
