package com.stampyt.service.impl;


import com.stampyt.repository.CarRepository;
import com.stampyt.repository.CarUpdateRepository;
import com.stampyt.repository.entity.Car;
import com.stampyt.repository.entity.Garage;
import com.stampyt.service.CarService;
import com.stampyt.service.GarageService;
import com.stampyt.service.converter.car.Car2CarBO;
import com.stampyt.service.converter.car.CarBO2Car;
import com.stampyt.service.exceptions.CarNotFound;
import com.stampyt.service.exceptions.MaxGarageCapacityReached;
import com.stampyt.service.exceptions.NoCarFoundForGarage;
import com.stampyt.service.model.CarBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class CarServiceImpl implements CarService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarServiceImpl.class);

    public CarServiceImpl(CarRepository carRepository, CarUpdateRepository carUpdateRepository, Car2CarBO car2CarBO, CarBO2Car carBO2Car, GarageService garageService) {
        this.carRepository = carRepository;
        this.carUpdateRepository = carUpdateRepository;
        this.car2CarBO = car2CarBO;
        this.carBO2Car = carBO2Car;
        this.garageService = garageService;
    }

    private CarRepository carRepository;
    private CarUpdateRepository carUpdateRepository;
    private Car2CarBO car2CarBO;
    private CarBO2Car carBO2Car;
    private GarageService garageService;

    @Override
    public Integer getCarNumber(UUID garageId) throws NoCarFoundForGarage {
        Garage garage = new Garage();
        garage.setId(garageId);
        Integer carNb = this.carRepository.countAllByGarage(garage);
        if (carNb == 0) {
            throw new NoCarFoundForGarage(garageId.toString());
        }
        return carNb;
    }

    @Override
    public CarBO addCar(UUID garageId, CarBO car) {
        Integer garageCapacity = this.garageService.getGarageMaxCapacity(garageId);
        Integer currentCarNumber = null;
        try {
            currentCarNumber = this.getCarNumber(garageId);
        } catch (NoCarFoundForGarage noCarFoundForGarage) {
            currentCarNumber = 0;
        }

        if (garageCapacity <= currentCarNumber) {
            throw new MaxGarageCapacityReached(garageId, garageCapacity, currentCarNumber);
        }

        Car carTosave = this.carBO2Car.convert(car);
        Garage associatedGarage = this.buildGarageWithId(garageId);
        carTosave.setGarage(associatedGarage);
        Car savedCar = this.carRepository.save(carTosave);

        return this.car2CarBO.convert(savedCar);
    }

    @Override
    public CarBO updateCarDetails(UUID carId, CarBO car) {
        Car savedCar = this.carUpdateRepository.save(this.carBO2Car.convert(car));
        if (savedCar == null) {
            throw new CarNotFound(carId);
        }
        return this.car2CarBO.convert(savedCar);
    }

    @Override
    public CarBO getCar(UUID carId) {
        Car foundedCar = this.carRepository.findOne(carId);
        if (foundedCar == null) {
            throw new CarNotFound(carId);
        }
        return this.car2CarBO.convert(foundedCar);
    }

    @Override
    @Transactional
    public boolean deleteAllCars(UUID garageId) {
        Garage garageIdRepo = this.buildGarageWithId(garageId);
        try {
            this.carRepository.deleteCarsByGarage(garageIdRepo);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn(e.getMessage(), e);
        }
        return true;
    }

    @Override
    public boolean deleteCar(UUID carId) {
        try {
            this.carRepository.delete(carId);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn(e.getMessage(), e);
        }
        return true;
    }


    @Override
    public Set<CarBO> getAllCars(UUID garageId, String color, Float minPrice, Float maxPrice) {
        Garage garageIdRepo = this.buildGarageWithId(garageId);
        List<Car> cars = this.carRepository.findCarsByGarageAndColorAndPriceGreaterThanEqualAndPriceLessThanEqual
                (garageIdRepo, color, minPrice, maxPrice);
        Set<CarBO> foundCars = new HashSet<>();
        if (cars != null) {
            for (Car car :
                    cars) {
                foundCars.add(this.car2CarBO.convert(car));
            }
        }
        return foundCars;
    }

    private Garage buildGarageWithId(UUID garageId) {
        Garage garage = new Garage();
        garage.setId(garageId);
        return garage;
    }
}
