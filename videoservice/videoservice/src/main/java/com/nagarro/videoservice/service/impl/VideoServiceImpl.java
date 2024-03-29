package com.nagarro.videoservice.service.impl;

import java.io.InputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nagarro.videoservice.exception.StorageException;
import com.nagarro.videoservice.model.Metadata;
import com.nagarro.videoservice.repository.MetadataRepository;
import com.nagarro.videoservice.service.VideoService;
import com.nagarro.videoservice.service.VideoStorageService;
import com.nagarro.videoservice.util.Range;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {
    private final VideoStorageService videoStorageService;
    private final MetadataRepository metadataRepository;

    public record ChunkWithMetadata(
            Metadata metadata,
            byte[] chunk) {
    }

    @Override
    @Transactional
    public String save(MultipartFile video) throws StorageException {
        try {
            Metadata metadata = new Metadata();
            metadata.setSize(video.getSize());
            metadata.setHttpContentType(video.getContentType());
            String uuid = metadataRepository.save(metadata).getId();
            videoStorageService.save(video, uuid);
            return uuid;
        } catch (Exception e) {
            throw new StorageException();
        }
    }

    @Override
    public ChunkWithMetadata fetchChunk(String uuid, Range range) throws StorageException {
        Metadata metadata = metadataRepository.findById(uuid)
                .orElseThrow(() -> new IllegalStateException("Metadata not found"));
        return new ChunkWithMetadata(metadata, readChunk(uuid, range, metadata.getSize()));

    }

    private byte[] readChunk(String uuid, Range range, long fileSize) throws StorageException {
        long startPosition = range.getRangeStart();
        long endPosition = range.getRangeEnd(fileSize);
        int chunkSize = (int) (endPosition - startPosition + 1);
        try {
            InputStream inputStream = videoStorageService.get(uuid, startPosition, chunkSize);
            System.out.println(inputStream.toString());
            try {
                return inputStream.readAllBytes();
            } finally {
                inputStream.close(); // Close the inputStream manually after reading
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            log.error("Exception occurred when trying to read file with ID = {}", uuid);
            throw new StorageException();
        }
    }

}
