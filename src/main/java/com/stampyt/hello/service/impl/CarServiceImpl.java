package com.stampyt.hello.service.impl;

import com.stampyt.hello.repository.CarRepository;
import com.stampyt.hello.repository.CarUpdateRepository;
import com.stampyt.hello.respository.entity.Car;
import com.stampyt.hello.respository.entity.Garage;
import com.stampyt.hello.service.CarService;
import com.stampyt.hello.service.GarageService;
import com.stampyt.hello.service.converter.car.Car2CarBO;
import com.stampyt.hello.service.converter.car.CarBO2Car;
import com.stampyt.hello.service.exceptions.CarNotFound;
import com.stampyt.hello.service.exceptions.MaxGarageCapacityReached;
import com.stampyt.hello.service.model.CarBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class CarServiceImpl implements CarService {

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
    public Integer getCarNumber(UUID garageId) {
        Garage garage = new Garage();
        garage.setId(garageId);
        return this.carRepository.countAllByGarage(garage);
    }

    @Override
    public CarBO addCar(UUID garageId, CarBO car) {
        Integer garageCapacity = this.garageService.getGarageMaxCapacity(garageId);
        Integer currentCarNumber = this.getCarNumber(garageId);

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
        this.carRepository.deleteCarsByGarage(garageIdRepo);
        return true;
    }

    @Override
    public boolean deleteCar(UUID carId) {
        this.carRepository.delete(carId);
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
