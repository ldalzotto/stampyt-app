package com.stampyt.hello.service.impl;

import com.stampyt.hello.repository.GarageRepository;
import com.stampyt.hello.respository.entity.Garage;
import com.stampyt.hello.service.GarageService;
import com.stampyt.hello.service.converter.garage.Garage2GarageBO;
import com.stampyt.hello.service.converter.garage.GarageBO2Garage;
import com.stampyt.hello.service.exceptions.GarageNotFound;
import com.stampyt.hello.service.model.GarageBO;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Garage createdGarage = this.garageRepository.save(garateToCreate);
        return this.garage2GarageBO.convert(createdGarage);
    }

    @Override
    @Transactional
    public GarageBO updateGarage(UUID garageId, GarageBO garageValuesToUpdate) {
        int rowsAffected = this.garageRepository.updateGarageDetails(garageId, garageValuesToUpdate.getName(),
                garageValuesToUpdate.getAddress(), garageValuesToUpdate.getCarStorageLimit());
        if (rowsAffected == 0) {
            throw new GarageNotFound(garageId.toString());
        }
        return garageValuesToUpdate;
    }

    @Override
    public boolean deleteGarage(UUID garageId) {
        this.garageRepository.delete(garageId);
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
    public Integer getCarNumber(UUID garageId) {
        //TODO
        return null;
    }

    private GarageBO prepareGarageForCreate(GarageBO garageBO) {
        garageBO.setCreationDate(DateTime.now(DateTimeZone.UTC));
        return garageBO;
    }

}
