package com.nagarro.postservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.postservice.dto.PostDTO;
import com.nagarro.postservice.exceptions.InvalidPostException;
import com.nagarro.postservice.exceptions.PostNotFoundException;
import com.nagarro.postservice.models.Post;
import com.nagarro.postservice.services.PostService;

@RestController
@RequestMapping("/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping()
    public ResponseEntity<Post> createPost(@RequestBody PostDTO postDTO) throws InvalidPostException {
        return new ResponseEntity<Post>(this.postService.createPost(postDTO), HttpStatus.CREATED);
    }
    
    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostDetails(@PathVariable String postId) throws PostNotFoundException{
    	Post post = postService.getPostDetails(postId);
        return ResponseEntity.ok(post);
    }

}
