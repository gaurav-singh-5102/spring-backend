package com.nagarro.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nagarro.entity.OTP;

public interface OTPRepository extends JpaRepository<OTP, Long> {

	Optional<OTP> findByEmail(String email);
}
