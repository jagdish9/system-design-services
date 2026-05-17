package com.sysdesign.pastebinservice.dto;

import java.time.LocalDateTime;

public class ExceptionResponse {
    private String message;
    private int statusCode;
    private LocalDateTime timestamp;

    private ExceptionResponse(Builder builder) {
        this.message = builder.message;
        this.statusCode = builder.statusCode;
        this.timestamp = builder.timestamp;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public static class Builder {
        private String message;
        private int statusCode;
        private LocalDateTime timestamp;

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder statusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder timeStamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ExceptionResponse build() {
            return new ExceptionResponse(this);
        }
    }
}
