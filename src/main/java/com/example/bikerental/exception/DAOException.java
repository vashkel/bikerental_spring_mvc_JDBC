package com.example.bikerental.exception;

import org.springframework.dao.DataAccessException;

public class DAOException extends Exception {
    private static final long serialVersionUID = 6622597961241028408L;

    public DAOException(String message) {
        super(message);
    }

    public DAOException(DataAccessException cause) {
        super(cause);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }


}