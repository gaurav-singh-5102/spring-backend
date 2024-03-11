package com.nagarro.websockets.model;

public class User {

	private String id;
    private String name;
    private String status;
    
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", status=" + status + "]";
    }

	
    
    
}
