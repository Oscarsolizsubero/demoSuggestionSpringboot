package com.example.demo.exception;

import lombok.Data;

import java.util.Date;

public class ErrorDetails {
    private Date timestamp;
    private String message;

    public ErrorDetails(Date timestamp, String message) {
        super();
        this.timestamp = timestamp;
        this.message = message;
    }
    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }
}
