package com.nagarro.userservice.dto;

import jakarta.validation.constraints.NotBlank;

public class ProfileUpdateDTO {

    @NotBlank
    private String id;
    private String name;
    @NotBlank
    private String email;
    private String image;

    public ProfileUpdateDTO() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
