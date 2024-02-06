package com.nagarro.postservice.services;

import java.util.Optional;

import com.nagarro.postservice.dto.PostDTO;
import com.nagarro.postservice.dto.PostPageDTO;
import com.nagarro.postservice.exceptions.InvalidPostException;
import com.nagarro.postservice.exceptions.PostNotFoundException;
import com.nagarro.postservice.models.Post;

public interface PostService {
    Post createPost(PostDTO postDTO, String author) throws InvalidPostException;

    PostPageDTO getPosts(Optional<Integer> page, Optional<Integer> size);
    Post getPostDetails(String postId) throws PostNotFoundException;
    
}
