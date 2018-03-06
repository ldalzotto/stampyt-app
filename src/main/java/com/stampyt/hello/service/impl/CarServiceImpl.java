package com.stampyt.hello.service.impl;

import com.stampyt.hello.service.CarService;
import com.stampyt.hello.service.exceptions.MaxGarageCapacityReached;
import com.stampyt.hello.service.model.CarBO;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class CarServiceImpl implements CarService {

    @Override
    public CarBO addCar(UUID garageId, CarBO car) throws MaxGarageCapacityReached {
        //TODO
        return null;
    }

    @Override
    public CarBO updateCarRegistrationNumber(UUID garageId, UUID carId, String registrationNumber) {
        //TODO
        return null;
    }

    @Override
    public CarBO getCar(UUID garageId, UUID carId) {
        //TODO
        return null;
    }

    @Override
    public boolean deleteAllCars(UUID garageId) {
        //TODO
        return false;
    }

    @Override
    public Set<CarBO> getAllCars(UUID garageId) {
        //TODO
        return null;
    }

    @Override
    public Set<CarBO> getAllColoredCars(UUID garageId, String color) {
        //TODO
        return null;
    }

    @Override
    public Set<CarBO> getAllCarsBetweenPrice(UUID garageId, Integer minPrice, Integer maxPrice) {
        return null;
    }
}
