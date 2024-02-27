package com.nagarro.Commentservice.DTO;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;

public class CommentDTO {

	@NotBlank(message = "postId cannot be blank.")
	private String postId;
	@NotBlank(message = "postAuthorId cannot be blank.")
	private String postAuthorId;
	@NotBlank(message = "Content cannot be blank.")
	private String content;	
	
	private LocalDateTime createdAt;
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
	public CommentDTO(String postId, String postAuthorId, String content, LocalDateTime createdAt) {
		super();
		this.postId = postId;
		this.postAuthorId = postAuthorId;
		this.content = content;
		this.createdAt = createdAt;
	}
	public CommentDTO() {
		super();
	}

}
