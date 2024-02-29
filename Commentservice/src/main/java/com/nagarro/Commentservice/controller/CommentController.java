package com.nagarro.Commentservice.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.Commentservice.DTO.CommentDTO;
import com.nagarro.Commentservice.DTO.CommentsPageDTO;
import com.nagarro.Commentservice.Exceptions.CommentNotFoundException;
import com.nagarro.Commentservice.Exceptions.InvalidCommentException;
import com.nagarro.Commentservice.Exceptions.InvalidRequestException;
import com.nagarro.Commentservice.models.Comment;
import com.nagarro.Commentservice.services.CommentService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/comments")
public class CommentController {
	
	private CommentService commentService;
	
	public CommentController(CommentService commentService)	{
		this.commentService=commentService;
	}
	@PostMapping
	public ResponseEntity<Comment> addComment(@RequestBody CommentDTO commentDTO,
			@RequestHeader("Authorization") String authHeader) throws InvalidCommentException{
		String token = authHeader.substring(7);
		return new ResponseEntity<Comment>(this.commentService.addComment(commentDTO, token), HttpStatus.CREATED);
	}
	@GetMapping("/count")
    public ResponseEntity<Integer> getCommentCount(@RequestParam("postId") String postId,
			@RequestHeader("Authorization") String authHeader) {
        int commentCount = commentService.getCommentCountByPostId(postId);
        return ResponseEntity.ok(commentCount);
    }
	@GetMapping("/post/{postId}")
    public ResponseEntity<?> getCommentsByPostId(@PathVariable String postId,
                                                             @RequestParam Optional<Integer> page,
                                                             @RequestParam Optional<Integer> size,
                                                             @RequestHeader("Authorization") String authHeader) {
		String token = authHeader.substring(7);
        return new ResponseEntity<CommentsPageDTO>(this.commentService.getCommentsByPostId(postId, page, size, token), HttpStatus.OK);
    }
	@DeleteMapping("/{commentId}")
	public ResponseEntity<?> deleteComment(@PathVariable String commentId, 
			@RequestHeader("Authorization") String authHeader) 
					throws CommentNotFoundException, InvalidRequestException
	{
		String token = authHeader.substring(7);
		commentService.deleteComment(commentId, token);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
