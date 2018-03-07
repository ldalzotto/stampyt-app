package com.stampyt.hello.service.exceptions;

public class DuplactedCarIdInSameGarage extends RuntimeException {
    public DuplactedCarIdInSameGarage(String message, Throwable cause) {
        super(message, cause);
    }
}
