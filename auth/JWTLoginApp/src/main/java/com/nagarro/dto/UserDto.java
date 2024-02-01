package com.nagarro.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDto {

	@NotBlank(message = "Name cannot be blank")
	@Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
	String name;
	
	@NotBlank(message = "Email cannot be blank")
	@Email
	String email;
	
	@NotBlank(message = "Password cannot be blank")
	@Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
	String password;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
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
	public UserDto(String name, String email, String password) {
		
		this.name = name;
		this.email = email;
		this.password = password;
	}
	public UserDto() {
		super();
	}
}
