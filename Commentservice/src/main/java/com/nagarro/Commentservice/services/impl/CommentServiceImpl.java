package com.nagarro.Commentservice.services.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.client.WebClient;

import com.nagarro.Commentservice.DTO.CommentDTO;
import com.nagarro.Commentservice.DTO.CommentSaveDTO;
import com.nagarro.Commentservice.DTO.CommentsPageDTO;
import com.nagarro.Commentservice.Exceptions.CommentNotFoundException;
import com.nagarro.Commentservice.Exceptions.InvalidCommentException;
import com.nagarro.Commentservice.Exceptions.InvalidRequestException;
import com.nagarro.Commentservice.models.Comment;
import com.nagarro.Commentservice.models.User;
import com.nagarro.Commentservice.repository.CommentRepository;
import com.nagarro.Commentservice.services.CommentService;
import com.nagarro.Commentservice.services.JWTService;

@Service
public class CommentServiceImpl implements CommentService {

	private JWTService jwtService;
	private CommentRepository commentRepository;
	private Validator validator;
	private final WebClient webClient;
	
	public CommentServiceImpl(JWTService jwtService, CommentRepository commentRepository, Validator validator, WebClient.Builder webClientBuilder) {
		this.jwtService = jwtService;
		this.commentRepository = commentRepository;
		this.validator=validator;
		this.webClient = webClientBuilder.baseUrl("http://localhost:8181").build();
	}
	
	@Override
	public Comment addComment(CommentDTO commentDTO, String token) throws InvalidCommentException {
		validateComment(commentDTO);
		String id = (String) jwtService.decodeJWT(token).get("jti");
		Comment comment = new Comment();
		comment.setPostId(commentDTO.getPostId());
		comment.setPostAuthorId(commentDTO.getPostAuthorId());
		comment.setCommentAuthorId(id);
		comment.setContent(commentDTO.getContent());
		comment.setCreatedAt(LocalDateTime.now());
		return this.commentRepository.save(comment);
	}
	
	@Override
    public int getCommentCountByPostId(String postId) {
        return commentRepository.countByPostId(postId);
    }
	
	@Override
	public CommentsPageDTO getCommentsByPostId(String postId, Optional<Integer> page, Optional<Integer> size, String token) {
	    int pageInt = page.orElse(1); 
	    int sizeInt = size.orElse(5); 
	    Pageable pageable = PageRequest.of(pageInt - 1, sizeInt, Sort.by(Sort.Direction.DESC, "createdAt"));
	    Page<Comment> commentPage;
	    commentPage = commentRepository.findByPostId(postId, pageable);
	    
	    CommentsPageDTO commentsPageDTO = new CommentsPageDTO();
	    commentsPageDTO.setComments(new ArrayList<CommentSaveDTO>());
	    commentsPageDTO.setPage(pageInt);
	    commentsPageDTO.setTotal(commentPage.getTotalElements());
	    commentsPageDTO.setFirst(commentPage.isFirst());
	    commentsPageDTO.setLast(commentPage.isLast());
	    commentsPageDTO.setSize(sizeInt);
	    
	    List<String> ids = new ArrayList<String>();
	    List<User> users = new ArrayList<User>();
	    
	    
	    commentPage.getContent().stream().forEach(comment ->{
	    	if(!ids.contains(comment.getCommentAuthorId())) {
	    		ids.add(comment.getCommentAuthorId());
	    		users.add(getUser(token, comment.getCommentAuthorId()));
	    		CommentSaveDTO commentSaveDTO = new CommentSaveDTO();
    			commentSaveDTO.setId(comment.getId());
                commentSaveDTO.setPostId(comment.getPostId());
                commentSaveDTO.setPostAuthorId(comment.getPostAuthorId());
                commentSaveDTO.setCommentAuthor(getUser(token, comment.getCommentAuthorId()));
                commentSaveDTO.setContent(comment.getContent());
                commentSaveDTO.setCreatedAt(comment.getCreatedAt());
                List<CommentSaveDTO> existingDTOs = commentsPageDTO.getComments();
                existingDTOs.add(commentSaveDTO);
                commentsPageDTO.setComments(existingDTOs);
	    	}
	    	else {
	    		User commentAuthor = getUser(token, comment.getCommentAuthorId());
	    		Optional<User> existingUser = users.stream()
							    				.filter(user->user.getId().equals(commentAuthor.getId()))
							    				.findFirst();
	    		if(existingUser.isPresent()) {
	    			List<CommentSaveDTO> existingDTOs = commentsPageDTO.getComments();
	    			CommentSaveDTO commentSaveDTO = new CommentSaveDTO();
	    			commentSaveDTO.setId(comment.getId());
	                commentSaveDTO.setPostId(comment.getPostId());
	                commentSaveDTO.setPostAuthorId(comment.getPostAuthorId());
	                commentSaveDTO.setCommentAuthor(existingUser.get());
	                commentSaveDTO.setContent(comment.getContent());
	                commentSaveDTO.setCreatedAt(comment.getCreatedAt());
	                existingDTOs.add(commentSaveDTO);
	                commentsPageDTO.setComments(existingDTOs);
	    		}
	    	}
	    });
	    return commentsPageDTO;
	}
	
	@Override
	public void deleteComment(String commentId, String token) throws CommentNotFoundException, InvalidRequestException {
		String userId = (String) jwtService.decodeJWT(token).get("jti");
		Optional<Comment> commentToBeDeleted = commentRepository.findById(commentId);
		if(commentToBeDeleted.isEmpty()) {
			throw new CommentNotFoundException("Comment not found!");
		}
		Comment comment = commentToBeDeleted.get();
		if(comment.getCommentAuthorId().equals(userId)||comment.getPostAuthorId().equals(userId)) {
			commentRepository.delete(comment);
		}
		else {
			throw new InvalidRequestException("You are not authorized to delete this comment");
		}
	}

	private void validateComment(CommentDTO commentDTO) throws InvalidCommentException {
        Errors errors = new BeanPropertyBindingResult(commentDTO, "entity");
        validator.validate(commentDTO, errors);
        if (errors.hasErrors()) {
            throw new InvalidCommentException(errors.getAllErrors());
        }
    }
	
	private User getUser(String token, String id) {
        try {
            return this.webClient.get()
                    .uri("/users/profile/" + id)
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .bodyToMono(User.class)
                    .block();
        } catch (Exception e) {
            User user = new User();
            user.setEmail(null);
            user.setId(id);
            user.setName("Deleted User");
            user.setImage(null);
            return user;
        }
    }
}
