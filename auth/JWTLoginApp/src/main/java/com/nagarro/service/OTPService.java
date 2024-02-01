package com.nagarro.service;

import com.nagarro.exceptions.OtpException;

public interface OTPService {
	void sendOtp(String email) throws OtpException;
	boolean verifyOtp(String email, String enteredOtp);
}
