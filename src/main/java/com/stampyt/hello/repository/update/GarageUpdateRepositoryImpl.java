package com.stampyt.hello.repository.update;

import com.stampyt.hello.repository.GarageUpdateRepository;
import com.stampyt.hello.respository.entity.Garage;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class GarageUpdateRepositoryImpl implements GarageUpdateRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    //TODO may me optimized (only one request)
    public Garage save(Garage garage) {

        Garage foundedGarage = entityManager.find(Garage.class, garage.getId());

        if (garage.getAddress() != null) {
            foundedGarage.setAddress(garage.getAddress());
        }
        if (garage.getCarStorageLimit() != null) {
            foundedGarage.setCarStorageLimit(garage.getCarStorageLimit());
        }
        if (garage.getName() != null) {
            foundedGarage.setName(garage.getName());
        }

        if (foundedGarage != null) {
            return foundedGarage;
        } else {
            return null;
        }


    }
}
