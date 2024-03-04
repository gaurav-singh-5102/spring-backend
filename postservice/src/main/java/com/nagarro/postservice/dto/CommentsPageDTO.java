package com.nagarro.postservice.dto;

import java.util.List;

public class CommentsPageDTO {

	private List<CommentSaveDTO> comments;
    private int page;
    private int size;
    private long total;
    private boolean first;
    private boolean last;
	public List<CommentSaveDTO> getComments() {
		return comments;
	}
	public void setComments(List<CommentSaveDTO> comments) {
		this.comments = comments;
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
	@Override
	public String toString() {
		return "CommentsPageDTO [comments=" + comments + ", page=" + page + ", size=" + size + ", total=" + total
				+ ", first=" + first + ", last=" + last + "]";
	}
    
}
