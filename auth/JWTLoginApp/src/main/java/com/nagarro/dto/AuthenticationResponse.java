package com.nagarro.dto;


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
