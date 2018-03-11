package com.stampyt.hello.repository.update;

import com.stampyt.hello.repository.CarUpdateRepository;
import com.stampyt.hello.respository.entity.Car;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class CarUpdateRepositoryImpl implements CarUpdateRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Car save(Car car) {

        Car foundedCar = this.entityManager.find(Car.class, car.getId());

        if (foundedCar == null) {
            return null;
        }

        if (car.getBrand() != null) {
            foundedCar.setBrand(car.getBrand());
        }
        if (car.getColor() != null) {
            foundedCar.setColor(car.getColor());
        }
        if (car.getCommisioningDate() != null) {
            foundedCar.setCommisioningDate(car.getCommisioningDate());
        }
        if (car.getRegistrationNumber() != null) {
            foundedCar.setRegistrationNumber(car.getRegistrationNumber());
        }

        return foundedCar;
    }
}
