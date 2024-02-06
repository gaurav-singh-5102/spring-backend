package com.nagarro.postservice.services.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.nagarro.postservice.dto.PostDTO;
import com.nagarro.postservice.dto.PostPageDTO;
import com.nagarro.postservice.exceptions.InvalidPostException;
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
    public Post createPost(PostDTO postDTO, String author) throws InvalidPostException {
        validatePost(postDTO);
        Post post = new Post();
        post.setAuthor(author);
        post.setContent(postDTO.getContent());
        post.setHeading(postDTO.getHeading());
        post.setCreatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    private void validatePost(PostDTO postDTO) throws InvalidPostException {
        Errors errors = new BeanPropertyBindingResult(postDTO, "entity");
        validator.validate(postDTO, errors);
        if (errors.hasErrors()) {
            throw new InvalidPostException(errors.getAllErrors());
        }
    }

    @Override
    public PostPageDTO getPosts(Optional<Integer> page, Optional<Integer> size) {
        int pageInt = page.isPresent() ? page.get() : 1;
        int sizeInt = size.isPresent() ? size.get() : 5;
        Pageable pageable = PageRequest.of(pageInt - 1, sizeInt);
        Page<Post> postPage = postRepository.findAll(pageable);
        PostPageDTO postPageDTO = new PostPageDTO();
        postPageDTO.setPosts(postPage.getContent());
        postPageDTO.setPage(pageInt);
        postPageDTO.setSize(postPage.getSize());
        postPageDTO.setTotal(postPage.getTotalElements());
        postPageDTO.setFirst(postPage.isFirst());
        postPageDTO.setLast(postPage.isLast());
        return postPageDTO;
    }

}
