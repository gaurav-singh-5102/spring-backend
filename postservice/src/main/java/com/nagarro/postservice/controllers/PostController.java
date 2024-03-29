package com.nagarro.postservice.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.postservice.dto.PostDTO;
import com.nagarro.postservice.dto.PostDetailsDTO;
import com.nagarro.postservice.dto.PostPageDTO;
import com.nagarro.postservice.exceptions.InvalidAuthorException;
import com.nagarro.postservice.exceptions.InvalidPostException;
import com.nagarro.postservice.exceptions.PostNotFoundException;
import com.nagarro.postservice.models.Post;
import com.nagarro.postservice.services.PostService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping()
    public ResponseEntity<Post> createPost(@RequestBody PostDTO postDTO,
            @RequestHeader("Authorization") String authHeader) throws InvalidPostException {
        String token = authHeader.substring(7);
        return new ResponseEntity<Post>(this.postService.createPost(postDTO, token), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getPosts(@RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size, @RequestParam Optional<String> feed,
            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        return new ResponseEntity<PostPageDTO>(this.postService.getPosts(page, size, feed, token), HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPostDetails(@PathVariable String postId,
    										   @RequestParam(required = false, defaultValue = "false") boolean includeComments,
    										   @RequestHeader("Authorization") String authHeader) throws PostNotFoundException {
        String token = authHeader.substring(7);
        PostDetailsDTO postDetailsDTO = postService.getPostDetails(postId, token, includeComments);
        return ResponseEntity.ok(postDetailsDTO);
    }
    
    @PutMapping("/{postId}/like")
    public ResponseEntity<Map<String, String>> likePost(@PathVariable String postId, @RequestHeader("Authorization") String authHeader) throws PostNotFoundException{
    	String token = authHeader.substring(7);
    	postService.likePost(postId, token);
    	Map<String, String> response = new HashMap<>();
    	response.put("message", "Likes incremented for post: " + postId);
    	return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable String postId)
            throws PostNotFoundException, InvalidAuthorException {
        postService.deletePost(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
