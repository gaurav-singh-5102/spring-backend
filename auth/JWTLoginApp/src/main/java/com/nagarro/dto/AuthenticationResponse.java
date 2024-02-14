package com.nagarro.dto;

import com.nagarro.entity.User;

public class AuthenticationResponse {

	private String message;
	private UserDetails user;
	
	public UserDetails getUser() {
		return user;
	}

	public void setUser(UserDetails user) {
		this.user = user;
	}

	public AuthenticationResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
