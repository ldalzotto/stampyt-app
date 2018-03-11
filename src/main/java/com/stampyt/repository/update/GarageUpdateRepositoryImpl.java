package com.stampyt.repository.update;


import com.stampyt.repository.GarageUpdateRepository;
import com.stampyt.repository.entity.Garage;
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

        if (foundedGarage == null) {
            return null;
        }

        if (garage.getAddress() != null) {
            foundedGarage.setAddress(garage.getAddress());
        }
        if (garage.getCarStorageLimit() != null) {
            foundedGarage.setCarStorageLimit(garage.getCarStorageLimit());
        }
        if (garage.getName() != null) {
            foundedGarage.setName(garage.getName());
        }
        return foundedGarage;

    }
}
