package com.nagarro.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.dto.EmailRequest;
import com.nagarro.exceptions.OtpException;
import com.nagarro.service.OTPService;
import com.nagarro.service.UserService;

@RestController
@CrossOrigin(origins="http://localhost:4200")
@RequestMapping("/auth/otp")
public class OTPController {

	@Autowired
	private OTPService otpService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/send")
	public ResponseEntity<String> sendOtp(@RequestBody EmailRequest emailRequest) {
	    try {
	    	
	        otpService.sendOtp(emailRequest.getEmail());
	        return new ResponseEntity<>("OTP sent successfully", HttpStatus.OK);
	    } catch (OtpException e) {
	        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	    }
	}
	
	@PostMapping("/verify")
    public ResponseEntity<String> verifyOtp(@RequestBody Map<String, String> request) throws OtpException {
        String email = request.get("email");
        String enteredOtp = request.get("enteredOtp");

        if (otpService.verifyOtp(email, enteredOtp)) {
        	String jwtToken = userService.authenticateAndGenerateToken(email, enteredOtp);
            return new ResponseEntity<>(jwtToken, HttpStatus.OK);
//            return new ResponseEntity<>("OTP verified successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid OTP or OTP expired", HttpStatus.BAD_REQUEST);
        }
    }
	
}
