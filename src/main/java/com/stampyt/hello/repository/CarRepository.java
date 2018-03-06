package com.stampyt.hello.repository;

import com.stampyt.hello.respository.entity.Car;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<Car, String> {
}
