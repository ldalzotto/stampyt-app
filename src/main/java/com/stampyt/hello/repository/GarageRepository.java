package com.stampyt.hello.repository;

import com.stampyt.hello.respository.entity.Garage;
import org.springframework.data.repository.CrudRepository;

public interface GarageRepository extends CrudRepository<Garage, String> {
}
