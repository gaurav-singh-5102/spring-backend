package com.nagarro.userservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nagarro.userservice.models.User;

public interface UserRepository extends MongoRepository<User, String> {

    boolean existsByEmail(String email);

    User findByEmail(String email);

}
