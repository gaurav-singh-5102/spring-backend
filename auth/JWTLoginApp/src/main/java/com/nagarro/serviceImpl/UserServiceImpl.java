package com.nagarro.serviceImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.client.WebClient;

import com.nagarro.dto.AuthDto;
import com.nagarro.dto.AuthenticationResponse;
import com.nagarro.dto.ErrorDto;
import com.nagarro.dto.UserDetails;
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
	
    private final WebClient webClient;

    public UserServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

	@Override
    @Transactional
    public ResponseEntity<?> addUser(@Validated UserDto userDTO) throws UserAlreadyExistsException {

		User existingUser = userRepository.findByEmail(userDTO.getEmail());
	    if (existingUser != null) {
	    	throw new UserAlreadyExistsException();
	    }
		
        String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
        UserDetails userDetails = this.savetoUserService(userDTO.getEmail(), encryptedPassword);
        User newUser = new User();
        newUser.setEmail(userDTO.getEmail());
        newUser.setPassword(encryptedPassword);
        newUser.setId(userDetails.getId());

        userRepository.save(newUser);

        return new ResponseEntity<>(userDetails, HttpStatus.CREATED);
    }
	
	@Override
    @Transactional
    public ResponseEntity<?> loginUser(AuthDto authDTO) {
        
        User existingUser = userRepository.findByEmail(authDTO.getEmail());
        UserDetails userDetails = this.fetchFromUserService(existingUser.getId(), authDTO.getEmail());

        if (existingUser != null) {
            if (passwordEncoder.matches(authDTO.getPassword(), existingUser.getPassword())) {
                
                AuthenticationResponse authenticationResponse = new AuthenticationResponse("Login successful");
                authenticationResponse.setUser(userDetails);
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
            String token = jwtService.generateToken(email);
            return token;
        } else {
            throw new OtpException();
        }
	}

	@Override
	public String getHomePage(String token) {
            jwtService.validateToken(token);
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String homePageContent = "Welcome, " + email + "! This is your home page content.";
            return homePageContent;
    }

    private UserDetails savetoUserService(String email, String password) {
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail(email);
        userDetails.setName(null);
        System.out.println(password);
        userDetails.setPassword(password);
        userDetails.setImage(null);
        return this.webClient.post()
                .uri("/profile")
                .bodyValue(userDetails)
                .header("Authorization", "Bearer " + jwtService.generateToken(userDetails.getEmail()))
                .retrieve()
                .bodyToMono(UserDetails.class)
                .block();
    }

    private UserDetails fetchFromUserService(String id, String email) {
        return this.webClient.get()
                .uri("/profile/" + id)
                .header("Authorization", "Bearer " + jwtService.generateToken(email))
                .retrieve()
                .bodyToMono(UserDetails.class)
                .block();
    }
	
}
