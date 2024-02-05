package com.nagarro.postservice.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.nagarro.postservice.dto.PostDTO;
import com.nagarro.postservice.exceptions.InvalidPostException;
import com.nagarro.postservice.models.Post;
import com.nagarro.postservice.services.PostService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("posts")
    public ResponseEntity<Post> createPost(@RequestBody PostDTO postDTO) throws InvalidPostException {
        return new ResponseEntity<Post>(this.postService.createPost(postDTO), HttpStatus.CREATED);
    }

}
