package com.stampyt.hello.service.exceptions;

import java.util.UUID;

public class CarNotFound extends RuntimeException {

    public CarNotFound(UUID carId) {
        super("The car : " + carId.toString() + " has not been found.");
    }
}
