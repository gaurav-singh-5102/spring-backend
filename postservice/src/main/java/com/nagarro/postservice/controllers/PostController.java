package com.nagarro.postservice.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.nagarro.postservice.dto.PostDTO;
import com.nagarro.postservice.dto.PostPageDTO;
import com.nagarro.postservice.exceptions.InvalidPostException;
import com.nagarro.postservice.models.Post;
import com.nagarro.postservice.service.JWTService;
import com.nagarro.postservice.services.PostService;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class PostController {

    private PostService postService;
    private JWTService jwtService;

    public PostController(PostService postService, JWTService jwtService) {
        this.postService = postService;
        this.jwtService = jwtService;
    }

    @PostMapping("posts")
    public ResponseEntity<Post> createPost(@RequestBody PostDTO postDTO,
            @RequestHeader("Authorization") String authHeader) throws InvalidPostException {
        String token = authHeader.substring(7);
        System.out.println(token);
        return new ResponseEntity<Post>(this.postService.createPost(postDTO,
                jwtService.extractUsername(token)), HttpStatus.CREATED);
    }

    @GetMapping("posts")
    public ResponseEntity<?> getPosts(@RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size) {
        return new ResponseEntity<PostPageDTO>(this.postService.getPosts(page, size), HttpStatus.OK);
    }

}
