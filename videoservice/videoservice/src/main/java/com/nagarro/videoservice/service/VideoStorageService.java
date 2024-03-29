package com.nagarro.videoservice.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nagarro.videoservice.exception.StorageException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VideoStorageService {

    public void save(MultipartFile file, String uuid) throws StorageException {
        try {
            Path videosDirectory = Paths.get("videos");
            if (!Files.exists(videosDirectory)) {
                Files.createDirectory(videosDirectory);
            }

            Path destination = Paths.get("videos", uuid);
            Files.copy(file.getInputStream(), destination);
        } catch (IOException ex) {
            throw new StorageException();
        }
    }

    public InputStream get(String uuid, long offset, long length) throws IOException {
        Path filePath = Paths.get("videos", uuid);
        if (!Files.exists(filePath)) {
            throw new FileNotFoundException();
        }
        if (offset < 0 || length < 0) {
            throw new IllegalArgumentException("Offset and length must be non-negative");
        }

        long fileSize = Files.size(filePath);
        if (offset >= fileSize) {
            throw new IllegalArgumentException("Offset exceeds file size");
        }

        long remainingBytes = fileSize - offset;
        long actualLength = Math.min(length, remainingBytes);

        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(filePath.toFile(), "r");
            randomAccessFile.seek(offset);
            return new FileInputStream(randomAccessFile.getFD()) {
                @Override
                public int available() throws IOException {
                    // Override available() to limit the number of bytes available
                    return (int) Math.min(super.available(), actualLength);
                }

                @Override
                public void close() throws IOException {
                }
            };
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Error opening file: " + e.getMessage());
        }
    }
}
