package com.stampyt.service.exceptions;

public class GarageNotFound extends RuntimeException {
    public GarageNotFound(String garageId) {
        super("The Garage with id : " + garageId + " has not been found.");
    }
}
