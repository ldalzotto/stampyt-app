package com.stampyt.service;


import com.stampyt.service.model.GarageBO;

import java.util.UUID;

public interface GarageService {

    GarageBO createGarage(GarageBO garage);

    GarageBO updateGarage(UUID garageId, GarageBO garageValuesToUpdate);

    boolean deleteGarage(UUID garageId);

    GarageBO getGarage(UUID garageId);

    Integer getGarageMaxCapacity(UUID garageId);

}
