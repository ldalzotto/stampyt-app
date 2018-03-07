package com.stampyt.hello.service.exceptions;

public class GarageNotFound extends RuntimeException {
    public GarageNotFound(String message) {
        super(message);
    }
}
