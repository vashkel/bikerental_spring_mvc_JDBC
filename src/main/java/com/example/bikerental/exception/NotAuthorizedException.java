package com.example.bikerental.exception;

public class NotAuthorizedException extends Throwable {
    public NotAuthorizedException(String cause) {
        super(cause);
    }
}
