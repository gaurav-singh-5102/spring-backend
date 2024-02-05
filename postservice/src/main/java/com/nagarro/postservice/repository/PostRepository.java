package com.nagarro.postservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nagarro.postservice.models.Post;

public interface PostRepository extends MongoRepository<Post, String> {

}
