package com.nagarro.Commentservice.services.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.nagarro.Commentservice.DTO.CommentDTO;
import com.nagarro.Commentservice.DTO.CommentsPageDTO;
import com.nagarro.Commentservice.Exceptions.InvalidCommentException;
import com.nagarro.Commentservice.models.Comment;
import com.nagarro.Commentservice.repository.CommentRepository;
import com.nagarro.Commentservice.services.CommentService;
import com.nagarro.Commentservice.services.JWTService;

@Service
public class CommentServiceImpl implements CommentService {

	private JWTService jwtService;
	private CommentRepository commentRepository;
	private Validator validator;
	
	public CommentServiceImpl(JWTService jwtService, CommentRepository commentRepository, Validator validator) {
		this.jwtService = jwtService;
		this.commentRepository = commentRepository;
		this.validator=validator;
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
	public CommentsPageDTO getCommentsByPostId(String postId, Optional<Integer> page, Optional<Integer> size) {
	    int pageInt = page.orElse(1); 
	    int sizeInt = size.orElse(5); 
	    Pageable pageable = PageRequest.of(pageInt - 1, sizeInt); 

	    Page<Comment> commentPage;
	    commentPage = commentRepository.findByPostId(postId, pageable);
	    
	    CommentsPageDTO commentsPageDTO = new CommentsPageDTO();
	    commentsPageDTO.setComments(commentPage.getContent());
	    commentsPageDTO.setPage(pageInt);
	    commentsPageDTO.setTotal(commentPage.getTotalElements());
	    commentsPageDTO.setFirst(commentPage.isFirst());
	    commentsPageDTO.setLast(commentPage.isLast());
	    
	    return commentsPageDTO;
	}
	
	private void validateComment(CommentDTO commentDTO) throws InvalidCommentException {
        Errors errors = new BeanPropertyBindingResult(commentDTO, "entity");
        validator.validate(commentDTO, errors);
        if (errors.hasErrors()) {
            throw new InvalidCommentException(errors.getAllErrors());
        }
    }
}
