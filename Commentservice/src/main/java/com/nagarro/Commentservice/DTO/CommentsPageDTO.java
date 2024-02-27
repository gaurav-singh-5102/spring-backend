package com.nagarro.Commentservice.DTO;

import java.util.List;

import com.nagarro.Commentservice.models.Comment;

public class CommentsPageDTO {

	private List<Comment> posts;
    private int page;
    private int size;
    private long total;
    private boolean first;
    private boolean last;
	public List<Comment> getPosts() {
		return posts;
	}
	public void setPosts(List<Comment> posts) {
		this.posts = posts;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public boolean isFirst() {
		return first;
	}
	public void setFirst(boolean first) {
		this.first = first;
	}
	public boolean isLast() {
		return last;
	}
	public void setLast(boolean last) {
		this.last = last;
	}
    
    
}
