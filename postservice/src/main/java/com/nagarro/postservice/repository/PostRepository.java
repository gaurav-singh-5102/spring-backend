package com.nagarro.postservice.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.nagarro.postservice.models.Post;

public interface PostRepository extends MongoRepository<Post, String> {

    Page<Post> findByAuthorNot(String author, Pageable pageable);

    Page<Post> findByAuthor(String author, Pageable pageable);

    Page<Post> findByAuthorEmailNot(String author, Pageable pageable);

    Page<Post> findByAuthorEmail(String feed, Pageable pageable);

    void delete(Post postToBeDeleted);

}
