package com.nagarro.websockets.model;

import java.util.HashMap;
import java.util.List;

public class ConnectMessage {
    private String senderName;
    private String senderId;
    private  List<User> users;
    private HashMap<String, List<ChatMessage>> history;

    
     public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public List<User> getUsers() {
        return users;
    }

    public void setUsers( List<User> users) {
        this.users = users;
    }

    public HashMap<String, List<ChatMessage>> getHistory() {
        return history;
    }

    public void setHistory(HashMap<String, List<ChatMessage>> history) {
        this.history = history;
    }

	@Override
	public String toString() {
		return "ConnectMessage [senderName=" + senderName + ", senderId=" + senderId + ", users=" + users + ", history="
				+ history + "]";
	}
    
}
