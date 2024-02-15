package com.nagarro.postservice.dto;

import jakarta.validation.constraints.NotBlank;

public class PostDTO {

    @NotBlank(message = "Content cannot be blank.")
    private String content;

    public PostDTO() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
