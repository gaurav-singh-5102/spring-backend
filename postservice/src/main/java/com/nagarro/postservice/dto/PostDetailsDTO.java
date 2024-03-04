package com.nagarro.postservice.dto;

import com.nagarro.postservice.models.Post;

public class PostDetailsDTO {
	private Post post;
    private CommentsPageDTO comments;
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
	}
	public CommentsPageDTO getComments() {
		return comments;
	}
	public void setComments(CommentsPageDTO comments) {
		this.comments = comments;
	}
	@Override
	public String toString() {
		return "PostDetailsDTO [post=" + post + ", comments=" + comments + "]";
	}
	public PostDetailsDTO() {
		super();
	}
	public PostDetailsDTO(Post post, CommentsPageDTO comments) {
		super();
		this.post = post;
		this.comments = comments;
	}
	
}
