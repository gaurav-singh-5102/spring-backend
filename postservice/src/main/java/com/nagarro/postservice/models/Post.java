package com.nagarro.postservice.models;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Document(collection = "posts")
public class Post {
    @Id
    private String id;

    @NotBlank(message = "Author cannot be blank.")
    private String author;

    @NotBlank(message = "Heading cannot be blank.")
    @Size(max = 255, message = "Heading cannot exceed more than 255 characters.")
    private String heading;

    @NotBlank(message = "Content cannot be blank.")
    private String content;

    private LocalDateTime createdAt;
    
//    private int likes;
    
    private ArrayList<String> likes;


	public ArrayList<String> getLikes() {
		return likes;
	}

	public void setLikes(ArrayList<String> likes) {
		this.likes = likes;
	}

	public Post() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
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
