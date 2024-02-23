package com.nagarro.Notificationservice.model;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

public class Notification {

	@NotNull(message="Notification message cannot be null")
	private String content;
	
	@NotNull(message="Sender cannot be null")
    private String sender;
	
	@NotNull(message="Receiver cannot be null")
    private String receiver;
	
    private boolean isGroupNotification;
    
    @NotNull(message="Timestamp cannot be null")
    private LocalDateTime timestamp;
    
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "Notification [content=" + content + ", sender=" + sender + ", receiver=" + receiver
				+ ", isGroupNotification=" + isGroupNotification + ", timestamp=" + timestamp + "]";
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
	public Notification() {
		super();
	}
	public Notification(String content, String sender, String receiver, boolean isGroupNotification, LocalDateTime timestamp) {
		super();
		this.content = content;
		this.sender = sender;
		this.receiver = receiver;
		this.isGroupNotification = isGroupNotification;
		this.timestamp = timestamp;
	}
	public Notification(String receiver2, String message) {
		super();
		this.receiver=receiver2;
		this.content=message;
	}
    
    
}
