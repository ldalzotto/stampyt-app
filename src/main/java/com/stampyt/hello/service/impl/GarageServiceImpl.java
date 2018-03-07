package com.stampyt.hello.service.impl;

import com.stampyt.hello.repository.GarageRepository;
import com.stampyt.hello.respository.entity.Car;
import com.stampyt.hello.respository.entity.Garage;
import com.stampyt.hello.service.GarageService;
import com.stampyt.hello.service.converter.garage.Garage2GarageBO;
import com.stampyt.hello.service.converter.garage.GarageBO2Garage;
import com.stampyt.hello.service.exceptions.DuplactedCarIdInSameGarage;
import com.stampyt.hello.service.exceptions.GarageNotFound;
import com.stampyt.hello.service.model.GarageBO;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GarageServiceImpl implements GarageService {

    public GarageServiceImpl(GarageRepository garageRepository, GarageBO2Garage garageBO2Garage, Garage2GarageBO garage2GarageBO) {
        this.garageRepository = garageRepository;
        this.garageBO2Garage = garageBO2Garage;
        this.garage2GarageBO = garage2GarageBO;
    }

    private GarageRepository garageRepository;
    private GarageBO2Garage garageBO2Garage;
    private Garage2GarageBO garage2GarageBO;

    @Override
    public GarageBO createGarage(GarageBO garage) {
        GarageBO identifiedGarage = this.prepareGarageForCreate(garage);
        Garage garateToCreate = this.garageBO2Garage.convert(identifiedGarage);
        Garage createdGarage = null;
        try {
            createdGarage = this.garageRepository.save(garateToCreate);
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            if (errorMessage.contains("Multiple representations of the same entity")
                    && errorMessage.contains(Car.class.getName())) {
                throw new DuplactedCarIdInSameGarage("Impossible to create multiple cars with the same registrationNumber.", e);
            }
            throw e;
        }
        return this.garage2GarageBO.convert(createdGarage);
    }

    @Override
    public GarageBO updateGarage(UUID garageId, GarageBO garageValuesToUpdate) {
        //TODO
        return null;
    }

    @Override
    public boolean deleteGarage(UUID garageId) {
        this.garageRepository.delete(garageId.toString());
        return true;
    }

    @Override
    public GarageBO getGarage(UUID garageId) {
        Garage foundGarage = this.garageRepository.findOne(garageId.toString());
        if (foundGarage == null) {
            throw new GarageNotFound("The Garage with id : " + garageId.toString() + " has not been found.");
        }
        return this.garage2GarageBO.convert(foundGarage);
    }

    @Override
    public Integer getCarNumber(UUID garageId) {
        //TODO
        return null;
    }

    private GarageBO prepareGarageForCreate(GarageBO garageBO) {
        garageBO.setId(UUID.randomUUID());
        garageBO.setCreationDate(DateTime.now(DateTimeZone.UTC));
        return garageBO;
    }
}
