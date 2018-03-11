package com.stampyt.repository;


import com.stampyt.repository.entity.Car;

public interface CarUpdateRepository {
    Car save(Car car);
}
