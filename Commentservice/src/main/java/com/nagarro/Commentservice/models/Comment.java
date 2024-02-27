package com.nagarro.Commentservice.models;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Comment {
	@Id
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	
	private String postId;
	
	private String postAuthorId;
	
	private String commentAuthorId;
	
	private String content;
	
	private LocalDateTime createdAt;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getPostAuthorId() {
		return postAuthorId;
	}

	public void setPostAuthorId(String postAuthorId) {
		this.postAuthorId = postAuthorId;
	}

	public String getCommentAuthorId() {
		return commentAuthorId;
	}

	public void setCommentAuthorId(String commentAuthorId) {
		this.commentAuthorId = commentAuthorId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	
	
}
