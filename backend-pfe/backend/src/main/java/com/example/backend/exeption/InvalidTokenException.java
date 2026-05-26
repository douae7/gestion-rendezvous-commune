package com.example.backend.exeption;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String message) { super(message); }
}