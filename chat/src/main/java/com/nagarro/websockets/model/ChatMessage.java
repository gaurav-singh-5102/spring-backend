package com.nagarro.websockets.model;

public class ChatMessage {
    private String content;
    private String senderId;
    private String senderName;
    private String receiverId;
    private String receiverName;
    private String group;
    private boolean isGroupChat;
    private MessageType type;
    private String timestamp;

    public enum MessageType {
        CHAT, LEAVE, JOIN, CONNECT
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public boolean isGroupChat() {
        return isGroupChat;
    }

    public void setGroupChat(boolean isGroupChat) {
        this.isGroupChat = isGroupChat;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

	@Override
	public String toString() {
		return "ChatMessage [content=" + content + ", senderId=" + senderId + ", senderName=" + senderName
				+ ", receiverId=" + receiverId + ", receiverName=" + receiverName + ", group=" + group
				+ ", isGroupChat=" + isGroupChat + ", type=" + type + ", timestamp=" + timestamp + "]";
	}

	

}
