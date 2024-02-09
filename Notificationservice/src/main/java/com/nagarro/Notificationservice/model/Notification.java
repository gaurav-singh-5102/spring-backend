package com.nagarro.Notificationservice.model;

public class Notification {

	private String content;
    private String sender;
    private String receiver;
    private boolean isGroupNotification;
    private String timestamp;
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
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public Notification() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Notification(String content, String sender, String receiver, boolean isGroupNotification, String timestamp) {
		super();
		this.content = content;
		this.sender = sender;
		this.receiver = receiver;
		this.isGroupNotification = isGroupNotification;
		this.timestamp = timestamp;
	}
    
    
}
