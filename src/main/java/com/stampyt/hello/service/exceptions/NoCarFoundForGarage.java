package com.stampyt.hello.service.exceptions;

public class NoCarFoundForGarage extends Exception {

    public NoCarFoundForGarage(String garageId) {
        super("No car found for garage : " + garageId);
    }
}
