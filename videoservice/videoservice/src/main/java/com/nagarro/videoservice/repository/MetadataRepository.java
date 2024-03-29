package com.nagarro.videoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nagarro.videoservice.model.Metadata;

@Repository
public interface MetadataRepository extends JpaRepository<Metadata, String> {

}
