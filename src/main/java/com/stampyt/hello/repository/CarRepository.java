package com.stampyt.hello.repository;

import com.stampyt.hello.respository.entity.Car;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CarRepository extends CrudRepository<Car, UUID> {
}
