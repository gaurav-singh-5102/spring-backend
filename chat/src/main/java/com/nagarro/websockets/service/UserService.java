package com.nagarro.websockets.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.nagarro.websockets.model.User;
import com.nagarro.websockets.util.JWTService;

@Service
public class UserService {
	
	private JWTService jwtService;
	private final WebClient userWebClient;
	
	private UserService(WebClient.Builder webClientBuilder, JWTService jwtService) {
		this.userWebClient = webClientBuilder.baseUrl("http://localhost:8181").build();
		this.jwtService=jwtService;
	}

	public User getUser(String token, String id) {
        return this.userWebClient.get()
                .uri("/users/profile/" + id)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(User.class)
                .block();
    }
}
