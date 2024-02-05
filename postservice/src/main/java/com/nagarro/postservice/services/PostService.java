package com.nagarro.postservice.services;

import com.nagarro.postservice.dto.PostDTO;
import com.nagarro.postservice.exceptions.InvalidPostException;
import com.nagarro.postservice.models.Post;

public interface PostService {
    Post createPost(PostDTO postDTO) throws InvalidPostException;
}
