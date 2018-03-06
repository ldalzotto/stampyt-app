package com.stampyt.hello.service;

import com.stampyt.hello.service.exceptions.MaxGarageCapacityReached;
import com.stampyt.hello.service.model.CarBO;

import java.util.Set;
import java.util.UUID;

public interface CarService {

    CarBO addCar(UUID garageId, CarBO car) throws MaxGarageCapacityReached;
    CarBO updateCarRegistrationNumber(UUID garageId, UUID carId, String registrationNumber);
    CarBO getCar(UUID garageId, UUID carId);
    boolean deleteAllCars(UUID garageId);
    Set<CarBO> getAllCars(UUID garageId);
    Set<CarBO> getAllColoredCars(UUID garageId, String color);
    Set<CarBO> getAllCarsBetweenPrice(UUID garageId, Integer minPrice, Integer maxPrice);

}
