package com.nagarro.postservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PostDTO {

    @NotBlank(message = "Heading cannot be blank.")
    @Size(max = 255, message = "Heading cannot exceed more than 255 characters.")
    private String content;

    @NotBlank(message = "Content cannot be blank.")
    private String heading;

    public PostDTO() {
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
