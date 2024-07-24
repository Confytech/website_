package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends RuntimeException{
    private String message;
    private HttpStatus httpStatus;

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public NotFoundException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
