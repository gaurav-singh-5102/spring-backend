package com.nagarro.postservice.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class NotificationRequestDto {
	@NotBlank(message="Notification content cannot be null")
	private String content;
	@NotBlank(message="Sender content cannot be null")
    private String sender;
	@NotBlank(message="Receiver content cannot be null")
    private String receiver;
    private boolean isGroupNotification;
    private LocalDateTime timestamp;
    
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public boolean isGroupNotification() {
		return isGroupNotification;
	}
	public void setGroupNotification(boolean isGroupNotification) {
		this.isGroupNotification = isGroupNotification;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	public NotificationRequestDto(String content, String sender, String receiver, boolean isGroupNotification,
			LocalDateTime timestamp) {
		super();
		this.content = content;
		this.sender = sender;
		this.receiver = receiver;
		this.isGroupNotification = isGroupNotification;
		this.timestamp = timestamp;
	}
	public NotificationRequestDto() {
		
	}
	@Override
	public String toString() {
		return "Notification [content=" + content + ", sender=" + sender + ", receiver=" + receiver
				+ ", isGroupNotification=" + isGroupNotification + ", timestamp=" + timestamp + "]";
	}
	
}
