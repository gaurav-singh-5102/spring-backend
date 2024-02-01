package com.nagarro.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AuthDto {
	
	@NotBlank(message = "Email cannot be blank")
	@Email
	private String email;
	
	@NotBlank(message = "Password cannot be blank")
	private String password;
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public AuthDto(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
	
	

}
