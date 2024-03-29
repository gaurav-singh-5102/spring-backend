package com.nagarro.videoservice.util;

import java.time.LocalDateTime;

public class ErrorDTO {
    private String error;
    private LocalDateTime timestamp;

    public ErrorDTO(String message) {
        this.error = message;
        this.timestamp = LocalDateTime.now();
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

}
