package com.stampyt.hello.service.exceptions;

public class InvalidIdFormat extends RuntimeException {

    public InvalidIdFormat(String message, Throwable cause) {
        super(message, cause);
    }
}
