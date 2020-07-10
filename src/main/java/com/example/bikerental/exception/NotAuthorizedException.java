package com.example.bikerental.exception;

public class NotAuthorizedException extends RuntimeException {
    public NotAuthorizedException(String cause) {
        super(cause);
    }
}
