package com.stampyt.hello.repository;

import com.stampyt.hello.respository.entity.Garage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GarageRepository extends JpaRepository<Garage, UUID> {

}
