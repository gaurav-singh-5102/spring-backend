package com.nagarro.dto;

public class ErrorDto {

	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public ErrorDto(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}
	
	
	
}
