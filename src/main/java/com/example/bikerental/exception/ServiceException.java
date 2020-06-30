package com.example.bikerental.exception;

public class ServiceException extends Exception {
    public ServiceException(String message, String cause) {
    }

    public ServiceException(String s) {

    }

    public ServiceException(String message, DAOException e) {

    }
}
