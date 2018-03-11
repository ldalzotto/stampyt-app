package com.stampyt.service.exceptions;

public class InvalidIdFormat extends RuntimeException {

    public InvalidIdFormat(String message, Throwable cause) {
        super(message, cause);
    }
}
