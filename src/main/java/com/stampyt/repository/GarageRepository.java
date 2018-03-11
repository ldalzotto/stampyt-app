package com.stampyt.repository;

import com.stampyt.repository.entity.Garage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface GarageRepository extends JpaRepository<Garage, UUID> {

    @Query("select g.carStorageLimit from Garage g where g.id=?1")
    Integer findCarStorageLimit(UUID garageId);

}
