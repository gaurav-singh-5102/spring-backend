package com.nagarro.Commentservice.services;

import java.util.Optional;

import com.nagarro.Commentservice.DTO.CommentDTO;
import com.nagarro.Commentservice.DTO.CommentsPageDTO;
import com.nagarro.Commentservice.Exceptions.CommentNotFoundException;
import com.nagarro.Commentservice.Exceptions.InvalidCommentException;
import com.nagarro.Commentservice.Exceptions.NotificationRequestException;
import com.nagarro.Commentservice.Exceptions.InvalidRequestException;
import com.nagarro.Commentservice.models.Comment;

public interface CommentService {

	Comment addComment(CommentDTO commentDTO, String token) throws InvalidCommentException, NotificationRequestException ;
	int getCommentCountByPostId(String postId);
	public CommentsPageDTO getCommentsByPostId(String postId, Optional<Integer> page, Optional<Integer> size, String token);
	public void deleteComment(String commentId, String token) throws CommentNotFoundException, InvalidRequestException;
}
