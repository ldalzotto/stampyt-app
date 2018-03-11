package com.stampyt.hello.service.exceptions;

import java.util.UUID;

public class GarageMaxCapacityNotDefined extends RuntimeException {

    public GarageMaxCapacityNotDefined(UUID garageId) {
        super("Garage max capacity is not defined for garageId : " + garageId.toString());
    }
}
