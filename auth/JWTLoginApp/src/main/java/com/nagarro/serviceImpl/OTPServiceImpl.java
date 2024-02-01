package com.nagarro.serviceImpl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.nagarro.entity.OTP;
import com.nagarro.exceptions.OtpException;
import com.nagarro.repository.OTPRepository;
import com.nagarro.service.OTPService;

@Service
public class OTPServiceImpl implements OTPService {

	@Autowired
	 private JavaMailSender javaMailSender;
	
	@Autowired
    private OTPRepository otpRepository;
	
	@Override
    public void sendOtp(String email) throws OtpException {
		
		Optional<OTP> otpEntityOptional = otpRepository.findByEmail(email);
		
		 otpEntityOptional.ifPresent(existingOtp -> {
		        System.out.println("Existing OTP ID: " + existingOtp.getId());

		        // Delete the already existing OTP
		        otpRepository.delete(existingOtp);
		    });
		

        try {
			String otp = generateOtp();

			// Save the OTP 
			OTP otpEntity = new OTP();
			otpEntity.setEmail(email);
			otpEntity.setOtp(otp);
			otpEntity.setExpiryTime(calculateExpiryTime());
			otpRepository.save(otpEntity);
			
			// Send the OTP via email
			sendEmail(email, "Your OTP", "Your OTP is: " + otp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new OtpException("Failed to send OTP. Please try again.");
		}
    }
	
	private LocalDateTime calculateExpiryTime() {
        return LocalDateTime.now().plusMinutes(10);
    }

	private void sendEmail(String to, String subject, String body) {
		SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        javaMailSender.send(message);
		
	}

	private String generateOtp() {
		// Generate a 6-digit random OTP
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
	}


	@Override
	public boolean verifyOtp(String email, String enteredOtp) {
	    // Retrieve the stored OTP from the database
	    Optional<OTP> otpEntityOptional = otpRepository.findByEmail(email);

	    if (otpEntityOptional.isPresent()) {
	        OTP otpEntity = otpEntityOptional.get();
	        
	        System.out.println("Entered OTP: [" + enteredOtp + "]");
	        System.out.println("Stored OTP: [" + otpEntity.getOtp() + "]");

	        // Check if the entered OTP matches the stored OTP 
	        if (enteredOtp.trim().equals(otpEntity.getOtp().trim())) {
	          
	            if (LocalDateTime.now().isBefore(otpEntity.getExpiryTime())) {
	                System.out.println("OTP verified successfully");
	                return true;
	            } else {
	                // OTP has expired
	            	otpRepository.delete(otpEntity);
	                System.out.println("OTP has expired");
	                return false;
	            }
	        } else {
	            System.out.println("Entered OTP does not match the stored OTP");
	        }
	    } else {
	        System.out.println("No OTP found for email: " + email);
	    }

	    // OTP verification failed
	    return false;
	}
	
}
