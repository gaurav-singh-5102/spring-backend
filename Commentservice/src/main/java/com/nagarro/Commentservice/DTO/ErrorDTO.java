package com.nagarro.Commentservice.DTO;

import java.time.LocalDateTime;

public class ErrorDTO {

	private String error;
    private LocalDateTime timestamp;

    public ErrorDTO(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public LocalDateTime getTimestamp() {
        return LocalDateTime.now();
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = LocalDateTime.now();
    }

}
