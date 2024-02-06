package com.nagarro.postservice.services.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.nagarro.postservice.dto.PostDTO;
import com.nagarro.postservice.exceptions.InvalidPostException;
import com.nagarro.postservice.exceptions.PostNotFoundException;
import com.nagarro.postservice.models.Post;
import com.nagarro.postservice.repository.PostRepository;
import com.nagarro.postservice.services.PostService;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private Validator validator;

    public PostServiceImpl(PostRepository postRepository, Validator validator) {
        this.postRepository = postRepository;
        this.validator = validator;
    }

    @Override
    public Post createPost(PostDTO postDTO) throws InvalidPostException {
        validatePost(postDTO);
        Post post = new Post();
        post.setAuthor(postDTO.getAuthor());
        post.setContent(postDTO.getContent());
        post.setHeading(postDTO.getHeading());
        post.setCreatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }
    
    @Override
    public Post getPostDetails(String postId) throws PostNotFoundException {
    	
    	Optional<Post> postOptional = postRepository.findById(postId);
    	return postOptional.orElseThrow(()-> new PostNotFoundException("Post Not found with id: "+postId));
    }

    private void validatePost(PostDTO postDTO) throws InvalidPostException {
        Errors errors = new BeanPropertyBindingResult(postDTO, "entity");
        validator.validate(postDTO, errors);
        if (errors.hasErrors()) {
            throw new InvalidPostException(errors.getAllErrors());
        }
    }

}
