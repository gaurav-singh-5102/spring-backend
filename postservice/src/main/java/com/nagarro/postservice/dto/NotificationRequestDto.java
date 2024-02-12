package com.nagarro.postservice.dto;

public class NotificationRequestDto {
	private String receiver;
	private String message;
	

	public String getReceiver() {
		return receiver;
	}


	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public NotificationRequestDto(String receiver, String message) {
		super();
		this.receiver = receiver;
		this.message = message;
	}


	public NotificationRequestDto() {
		super();
		// TODO Auto-generated constructor stub
	}


	

	
}
