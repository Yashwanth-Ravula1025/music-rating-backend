package com.example.userservice.exception;

public class InvalidRatingException extends Exception {
    public InvalidRatingException(String message) {
        super(message);
    }
}
