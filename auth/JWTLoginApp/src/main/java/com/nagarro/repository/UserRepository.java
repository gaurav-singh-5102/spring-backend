package com.nagarro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nagarro.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

	User findByEmail(String email);

	
}
