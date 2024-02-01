package com.nagarro.service;

import org.springframework.http.ResponseEntity;

import com.nagarro.dto.AuthDto;
import com.nagarro.dto.UserDto;
import com.nagarro.exceptions.OtpException;
import com.nagarro.exceptions.UserAlreadyExistsException;

public interface UserService {

	ResponseEntity<?> addUser(UserDto userDto) throws UserAlreadyExistsException;
	ResponseEntity<?> loginUser(AuthDto authDto);
	public String authenticateAndGenerateToken(String email, String enteredOtp) throws OtpException;
	public String getHomePage(String token);
}
