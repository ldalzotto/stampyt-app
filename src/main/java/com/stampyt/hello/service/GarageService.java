package com.stampyt.hello.service;

import com.stampyt.hello.service.model.GarageBO;

import java.util.UUID;

public interface GarageService {

    GarageBO createGarage(GarageBO garage);
    GarageBO updateGarage(UUID garageId, GarageBO garageValuesToUpdate);
    boolean deleteGarage(UUID garageId);
    GarageBO getGarage(UUID garageId);
    Integer getCarNumber(UUID garageId);

}
