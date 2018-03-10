package com.stampyt.hello.repository;

import com.stampyt.hello.respository.entity.Garage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface GarageRepository extends JpaRepository<Garage, UUID> {

    @Modifying
    @Query("UPDATE Garage g set g.name=?2, g.address=?3, g.carStorageLimit=?4 where g.id=?1")
    int updateGarageDetails(UUID id, String name, String address, Integer carStorageLimit);

}
