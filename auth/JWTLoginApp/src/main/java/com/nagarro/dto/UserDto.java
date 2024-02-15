package com.nagarro.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDto {
	@NotBlank(message = "Email cannot be blank")
	@Email
	String email;
	
	@NotBlank(message = "Password cannot be blank")
	@Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
	String password;

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

	public UserDto(String email, String password) {

		this.email = email;
		this.password = password;
	}

	public UserDto() {
	}
}
