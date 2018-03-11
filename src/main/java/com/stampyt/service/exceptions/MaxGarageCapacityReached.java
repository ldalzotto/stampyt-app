package com.stampyt.service.exceptions;

import java.util.UUID;

public class MaxGarageCapacityReached extends RuntimeException {

    public MaxGarageCapacityReached(UUID garageId, Integer maxCapacity, Integer currentCapacity) {
        super("Cannot add any more car on garage : " + garageId.toString() + ". Storage limit already reached ! MaxCapacity : " +
                maxCapacity + ", currentNumberOfCars : " + currentCapacity);
    }
}
