package com.stampyt.hello.service;

import com.stampyt.hello.service.model.CarBO;

import java.util.Set;
import java.util.UUID;

public interface CarService {

    Integer getCarNumber(UUID garageId);

    CarBO addCar(UUID garageId, CarBO car);

    CarBO updateCarDetails(UUID carId, CarBO car);

    CarBO getCar(UUID carId);

    boolean deleteAllCars(UUID garageId);

    boolean deleteCar(UUID carId);

    Set<CarBO> getAllCars(UUID garageId, String color, Float minPrice, Float maxPrice);

}
