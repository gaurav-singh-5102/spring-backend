package com.nagarro.postservice.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.nagarro.postservice.dto.PostDTO;
import com.nagarro.postservice.dto.PostPageDTO;
import com.nagarro.postservice.exceptions.InvalidPostException;
import com.nagarro.postservice.models.Post;
import com.nagarro.postservice.services.PostService;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("posts")
    public ResponseEntity<?> getPosts(@RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size) {
        return new ResponseEntity<PostPageDTO>(this.postService.getPosts(page, size), HttpStatus.OK);
    }

}
