package com.nagarro.postservice.services;

import java.util.Optional;

import com.nagarro.postservice.dto.PostDTO;
import com.nagarro.postservice.dto.PostDetailsDTO;
import com.nagarro.postservice.dto.PostPageDTO;
import com.nagarro.postservice.exceptions.InvalidAuthorException;
import com.nagarro.postservice.exceptions.InvalidPostException;
import com.nagarro.postservice.exceptions.PostNotFoundException;
import com.nagarro.postservice.models.Post;

public interface PostService {
    Post createPost(PostDTO postDTO, String token) throws InvalidPostException;

    PostPageDTO getPosts(Optional<Integer> page, Optional<Integer> size, Optional<String> feed, String token);
    PostDetailsDTO getPostDetails(String postId, String token, boolean includeComments) throws PostNotFoundException;
    void likePost(String postId, String token) throws PostNotFoundException;
    void deletePost(String postId) throws PostNotFoundException, InvalidAuthorException;
    
}
