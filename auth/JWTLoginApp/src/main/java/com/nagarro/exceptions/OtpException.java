package com.nagarro.exceptions;

public class OtpException extends Exception {

	public OtpException() {
        super("Invalid OTP");
    }

	public OtpException(String string) {
		super(string);
	}
}
