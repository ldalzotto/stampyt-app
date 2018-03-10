package com.stampyt.hello.service.exceptions;

public class DuplactedCarIdInSameGarage extends RuntimeException {
    public DuplactedCarIdInSameGarage(Throwable cause) {
        super("Impossible to create multiple cars with the same registrationNumber.", cause);
    }
}
