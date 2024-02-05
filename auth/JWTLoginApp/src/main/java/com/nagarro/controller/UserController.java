package com.nagarro.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.nagarro.dto.AuthDto;
import com.nagarro.dto.AuthenticationResponse;
import com.nagarro.dto.UserDto;
import com.nagarro.entity.User;
import com.nagarro.exceptions.OtpException;
import com.nagarro.exceptions.UserAlreadyExistsException;
import com.nagarro.service.JWTService;
import com.nagarro.service.UserService;

import jakarta.validation.Valid;


@RestController
@CrossOrigin(origins="http://localhost:4200")
@RequestMapping("/user")
public class UserController {
	
	@Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;

    @Value("${spring.security.oauth2.client.registration.linkedin.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.linkedin.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.linkedin.redirect-uri}")
    private String redirect;

	@GetMapping("/welcome") 
    public String welcome() { 
        return "Welcome this endpoint is not secure"; 
    }
	
	@GetMapping("/hello")
	public String hello() {
		return "Hello!";
	}
	
	
	@PostMapping("/addNewUser")
    public ResponseEntity<?> addUser(@RequestBody @Validated UserDto userDTO) throws UserAlreadyExistsException{

		ResponseEntity<?> responseEntity = userService.addUser(userDTO);

		Object savedUser = responseEntity.getBody();
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser); // Return 201 Created along with saved user
    }
	
	@PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody @Valid AuthDto authDTO) {
		ResponseEntity<?> responseEntity = userService.loginUser(authDTO);
		if (responseEntity.getStatusCode() == HttpStatus.OK) {
	        AuthenticationResponse authenticationResponse = (AuthenticationResponse) responseEntity.getBody();
	        User existingUser = authenticationResponse.getUser();
	        
	    }
        return responseEntity;
    }
	
	@PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtpAndGenerateToken(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String enteredOtp = request.get("enteredOtp");

        try {
            String jwtToken = userService.authenticateAndGenerateToken(email, enteredOtp);
            return new ResponseEntity<>(jwtToken, HttpStatus.OK);
        } catch (OtpException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
	@GetMapping("/home")
    public ResponseEntity<String> getHomePage(@RequestHeader("Authorization") String token) {
        try {
            String homePageContent = userService.getHomePage(token.substring(7)); 
            return new ResponseEntity<>(homePageContent, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve home page: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/linkedin-auth")
    public ResponseEntity<?> getLinkedInToken(@RequestParam("code") String code) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(headers);
        String url = "https://www.linkedin.com/oauth/v2/accessToken?grant_type=authorization_code&code=" + code
                + "&redirect_uri=" + redirect + "&client_id=" + clientId + "&client_secret=" + clientSecret;
        try {
            ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, HashMap.class);
            HashMap<String, String> responseMap = (HashMap<String, String>) response.getBody();
            HashMap<?, ?> userDetails = jwtService.decodeJWT(responseMap.get("id_token"));
            String token = this.jwtService.generateToken((String) userDetails.get("email"));
            HashMap<String, Object> tokenMap = new HashMap<>();
            User user = new User();
            user.setName((String) userDetails.get("name"));
            user.setEmail((String) userDetails.get("email"));
            tokenMap.put("token", token);
            tokenMap.put("user", user);
            return new ResponseEntity<HashMap<String, Object>>(tokenMap, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Could not get acess token",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
