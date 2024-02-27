package com.nagarro.Commentservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nagarro.Commentservice.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, String> {

	int countByPostId(String postId);
	Page<Comment> findByPostId(String postId, Pageable pageable);
}
