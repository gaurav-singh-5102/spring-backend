package com.nagarro.postservice.services.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.nagarro.postservice.dto.PostDTO;
import com.nagarro.postservice.dto.PostPageDTO;
import com.nagarro.postservice.exceptions.InvalidPostException;
import com.nagarro.postservice.exceptions.PostNotFoundException;
import com.nagarro.postservice.models.Notification;
import com.nagarro.postservice.models.Post;
import com.nagarro.postservice.models.User;
import com.nagarro.postservice.repository.PostRepository;
import com.nagarro.postservice.services.JWTService;
import com.nagarro.postservice.services.PostService;

import reactor.core.publisher.Mono;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private Validator validator;
    private final WebClient webClient;
    private final WebClient userWebclient;
    private JWTService jwtService;
    
    private String userServiceBaseUrl;
    
    private String notificationServiceBaseUrl;
    
    public PostServiceImpl(PostRepository postRepository, Validator validator, WebClient.Builder webClientBuilder,
            JWTService jwtService) {
        this.postRepository = postRepository;
        this.validator = validator;
        this.webClient = webClientBuilder.baseUrl("http://localhost:8084").build();
        this.userWebclient = webClientBuilder.baseUrl("http://localhost:8181").build();
        this.jwtService = jwtService;
    }

    @Override
    public Post createPost(PostDTO postDTO, String token) throws InvalidPostException {
        validatePost(postDTO);
        Post post = new Post();
        post.setAuthor(getUser(token));
        post.setContent(postDTO.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setLikes(new ArrayList<String>());
        return postRepository.save(post);
    }
    
    @Override
    public Post getPostDetails(String postId) throws PostNotFoundException {
    	
    	Optional<Post> postOptional = postRepository.findById(postId);
    	return postOptional.orElseThrow(()-> new PostNotFoundException("Post Not found with id: "+postId));
    }
    
    @Override
    public void likePost(String postId, String token) throws PostNotFoundException {
    	ArrayList<String> likes = new ArrayList<>();
    	String userId=(String) jwtService.decodeJWT(token).get("jti");
    	String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Optional<Post> postOptional = postRepository.findById(postId);
    	Post post = postOptional.orElseThrow(()->new PostNotFoundException("Post not found with id: " + postId));
    	likes = post.getLikes();
    	if(likes.contains(userId)) {    		
    		likes.remove(userId);
    	}
    	else {
    		likes.add(userId);
    		sendNotification(userId, username, post).subscribe();
    	}
    	
    	post.setLikes(likes);
    	postRepository.save(post);
    }
    
    
    private Mono<Void> sendNotification(String userId, String username, Post post) {
        // Construct the Notification object
        Notification notification = new Notification();
        notification.setContent(username+ " liked your post!");
        notification.setSender(username);
        notification.setReceiver(post.getAuthor().getId());
        notification.setGroupNotification(false);
        notification.setTimestamp(LocalDateTime.now());

        return webClient.post()
                .uri("/sendNotification")
                .body(BodyInserters.fromValue(notification))
                .retrieve()
                .toBodilessEntity()
                .then();
    }

    private void validatePost(PostDTO postDTO) throws InvalidPostException {
        Errors errors = new BeanPropertyBindingResult(postDTO, "entity");
        validator.validate(postDTO, errors);
        if (errors.hasErrors()) {
            throw new InvalidPostException(errors.getAllErrors());
        }
    }

    @Override
    public PostPageDTO getPosts(Optional<Integer> page, Optional<Integer> size, Optional<String> feedType) {

        Page<Post> postPage;
        int pageInt = page.isPresent() ? page.get() : 1;
        int sizeInt = size.isPresent() ? size.get() : 5;
        String feed = feedType.isPresent() ? feedType.get() : "all";
        String author = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Pageable pageable = PageRequest.of(pageInt - 1, sizeInt);
        if (feed.equalsIgnoreCase("all")) {
            postPage = postRepository.findByAuthorEmailNot(author, pageable);
        } else {
            postPage = postRepository.findByAuthorEmail(feed, pageable);
        }
        PostPageDTO postPageDTO = new PostPageDTO();
        postPageDTO.setPosts(postPage.getContent());
        postPageDTO.setPage(pageInt);
        postPageDTO.setSize(postPage.getSize());
        postPageDTO.setTotal(postPage.getTotalElements());
        postPageDTO.setFirst(postPage.isFirst());
        postPageDTO.setLast(postPage.isLast());
        return postPageDTO;
    }

    private User getUser(String token) {
        String id = (String) jwtService.decodeJWT(token).get("jti");
        return this.userWebclient.get()
                .uri("/users/profile/" + id)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(User.class)
                .block();
    }
}
