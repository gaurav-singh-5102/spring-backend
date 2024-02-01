package com.nagarro.serviceImpl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.nagarro.dto.AuthDto;
import com.nagarro.dto.AuthenticationResponse;
import com.nagarro.dto.ErrorDto;
import com.nagarro.dto.UserDto;
import com.nagarro.entity.User;
import com.nagarro.exceptions.OtpException;
import com.nagarro.exceptions.UserAlreadyExistsException;
import com.nagarro.repository.UserRepository;
import com.nagarro.service.JWTService;
import com.nagarro.service.OTPService;
import com.nagarro.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.InvalidKeyException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private OTPService otpService;
	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
    private AuthenticationManager authenticationManager;
	
	@Override
    @Transactional
    public ResponseEntity<?> addUser(@Validated UserDto userDTO) throws UserAlreadyExistsException {

		User existingUser = userRepository.findByEmail(userDTO.getEmail());
	    if (existingUser != null) {
//	    	ErrorDto errorDto = new ErrorDto("User with this email already exists");
//	        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorDto);
	    	throw new UserAlreadyExistsException();
	    }
		
        User newUser = new User();
        newUser.setName(userDTO.getName());
        newUser.setEmail(userDTO.getEmail());
        String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
        newUser.setPassword(encryptedPassword);

        User savedUser = userRepository.save(newUser);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
	
	@Override
    @Transactional
    public ResponseEntity<?> loginUser(AuthDto authDTO) {
        
        User existingUser = userRepository.findByEmail(authDTO.getEmail());

        if (existingUser != null) {
            if (passwordEncoder.matches(authDTO.getPassword(), existingUser.getPassword())) {
                
                AuthenticationResponse authenticationResponse = new AuthenticationResponse("Login successful");
                authenticationResponse.setUser(existingUser);
                return ResponseEntity.ok(authenticationResponse);
            } else {
                ErrorDto errorDTO = new ErrorDto("Invalid credentials");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorDTO);
            }
        } else {
            ErrorDto errorDTO = new ErrorDto("Invalid credentials");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorDTO);
        }
    }

	@Override
	public String authenticateAndGenerateToken(String email, String enteredOtp) throws OtpException, InvalidKeyException {
		// Verify OTP
        if (otpService.verifyOtp(email, enteredOtp)) {
            // Authenticate the user using Spring Security's AuthenticationManager
        	Claims claims = Jwts.claims().setSubject(email);
            String token = Jwts.builder()
                    .setClaims(claims)
                    .signWith(SignatureAlgorithm.HS256, "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437")
                    .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24 hours
                    .compact();
            System.out.println(token);
            return token;
        } else {
            throw new OtpException();
        }
	}

	@Override
	public String getHomePage(String token) {
        Claims claims = validateToken(token);
        String email = claims.getSubject();
        String homePageContent = "Welcome, " + email + "! This is your home page content.";
        return homePageContent;
    }

    private Claims validateToken(String token) {
        // Validate the token and extract claims
        try {
            return Jwts.parser().setSigningKey("5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437").parseClaimsJws(token).getBody();
        } catch (Exception e) {
            throw new RuntimeException("Invalid or expired token");
        }
    
	}
	
}
