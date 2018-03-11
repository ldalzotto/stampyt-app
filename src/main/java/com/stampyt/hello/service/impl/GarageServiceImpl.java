package com.stampyt.hello.service.impl;

import com.stampyt.hello.repository.GarageRepository;
import com.stampyt.hello.repository.GarageUpdateRepository;
import com.stampyt.hello.respository.entity.Garage;
import com.stampyt.hello.service.GarageService;
import com.stampyt.hello.service.converter.garage.Garage2GarageBO;
import com.stampyt.hello.service.converter.garage.GarageBO2Garage;
import com.stampyt.hello.service.exceptions.GarageMaxCapacityNotDefined;
import com.stampyt.hello.service.exceptions.GarageNotFound;
import com.stampyt.hello.service.model.GarageBO;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GarageServiceImpl implements GarageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GarageServiceImpl.class);

    public GarageServiceImpl(GarageRepository garageRepository, GarageBO2Garage garageBO2Garage, Garage2GarageBO garage2GarageBO, GarageUpdateRepository garageUpdateRepository) {
        this.garageRepository = garageRepository;
        this.garageBO2Garage = garageBO2Garage;
        this.garage2GarageBO = garage2GarageBO;
        this.garageUpdateRepository = garageUpdateRepository;
    }

    private GarageRepository garageRepository;
    private GarageBO2Garage garageBO2Garage;
    private Garage2GarageBO garage2GarageBO;
    private GarageUpdateRepository garageUpdateRepository;

    @Override
    public GarageBO createGarage(GarageBO garage) {
        GarageBO identifiedGarage = this.prepareGarageForCreate(garage);
        Garage garateToCreate = this.garageBO2Garage.convert(identifiedGarage);
        Garage createdGarage = this.garageRepository.save(garateToCreate);
        return this.garage2GarageBO.convert(createdGarage);
    }

    @Override
    public GarageBO updateGarage(UUID garageId, GarageBO garageValuesToUpdate) {
        garageValuesToUpdate.setId(garageId);
        Garage updatedGarage = this.garageUpdateRepository.save(this.garageBO2Garage.convert(garageValuesToUpdate));
        if (updatedGarage == null) {
            throw new GarageNotFound(garageId.toString());
        }

        return garageValuesToUpdate;
    }

    @Override
    public boolean deleteGarage(UUID garageId) {
        try {
            this.garageRepository.delete(garageId);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn(e.getMessage(), e);
        }
        return true;
    }

    @Override
    public GarageBO getGarage(UUID garageId) {
        Garage foundGarage = this.garageRepository.findOne(garageId);
        if (foundGarage == null) {
            throw new GarageNotFound(garageId.toString());
        }
        return this.garage2GarageBO.convert(foundGarage);
    }

    @Override
    public Integer getGarageMaxCapacity(UUID garageId) {
        Integer maxCapacity = this.garageRepository.findCarStorageLimit(garageId);
        if (maxCapacity == null) {
            throw new GarageMaxCapacityNotDefined(garageId);

        }
        return maxCapacity;
    }

    private GarageBO prepareGarageForCreate(GarageBO garageBO) {
        garageBO.setCreationDate(DateTime.now(DateTimeZone.UTC));
        return garageBO;
    }

}
