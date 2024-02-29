package com.nagarro.Commentservice.DTO;

import java.time.LocalDateTime;

import com.nagarro.Commentservice.models.User;

public class CommentSaveDTO {

private String id;
	
	private String postId;
	
	private String postAuthorId;
	
	private User commentAuthor;
	
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

	public User getCommentAuthor() {
		return commentAuthor;
	}

	public void setCommentAuthor(User commentAuthor) {
		this.commentAuthor = commentAuthor;
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

	@Override
	public String toString() {
		return "CommentSaveDTO [id=" + id + ", postId=" + postId + ", postAuthorId=" + postAuthorId + ", commentAuthor="
				+ commentAuthor + ", content=" + content + ", createdAt=" + createdAt + "]";
	}
	
	
}
