package com.nagarro.videoservice.service;

import org.springframework.web.multipart.MultipartFile;

import com.nagarro.videoservice.exception.StorageException;
import com.nagarro.videoservice.service.impl.VideoServiceImpl;
import com.nagarro.videoservice.util.Range;

public interface VideoService {
    String save(MultipartFile video) throws StorageException;

    VideoServiceImpl.ChunkWithMetadata fetchChunk(String uuid , Range range) throws StorageException;
}
