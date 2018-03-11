package com.stampyt.service;


import com.stampyt.service.exceptions.NoCarFoundForGarage;
import com.stampyt.service.model.CarBO;

import java.util.Set;
import java.util.UUID;

public interface CarService {

    Integer getCarNumber(UUID garageId) throws NoCarFoundForGarage;

    CarBO addCar(UUID garageId, CarBO car);

    CarBO updateCarDetails(UUID carId, CarBO car);

    CarBO getCar(UUID carId);

    boolean deleteAllCars(UUID garageId);

    boolean deleteCar(UUID carId);

    Set<CarBO> getAllCars(UUID garageId, String color, Float minPrice, Float maxPrice);

}
