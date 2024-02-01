package com.nagarro.dto;

import com.nagarro.entity.User;

public class AuthenticationResponse {

	private String message;
	private User user;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public AuthenticationResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
