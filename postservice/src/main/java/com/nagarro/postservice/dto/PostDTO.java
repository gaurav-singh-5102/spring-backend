package com.nagarro.postservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PostDTO {

    @NotBlank(message = "Author cannot be blank.")
    private String author;

    @NotBlank(message = "Heading cannot be blank.")
    @Size(max = 255, message = "Heading cannot exceed more than 255 characters.")
    private String content;

    @NotBlank(message = "Content cannot be blank.")
    private String heading;

    public PostDTO() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

}
