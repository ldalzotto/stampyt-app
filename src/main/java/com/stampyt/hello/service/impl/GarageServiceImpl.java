package com.stampyt.hello.service.impl;

import com.stampyt.hello.service.GarageService;
import com.stampyt.hello.service.model.GarageBO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GarageServiceImpl implements GarageService {

    @Override
    public GarageBO createGarage(GarageBO garage) {
        //TODO
        return null;
    }

    @Override
    public GarageBO updateGarage(UUID garageId, GarageBO garageValuesToUpdate) {
        //TODO
        return null;
    }

    @Override
    public boolean deleteGarage(UUID garageId) {
        //TODO
        return false;
    }

    @Override
    public GarageBO getGarage(UUID garageId) {
        //TODO
        return null;
    }

    @Override
    public Integer getCarNumber(UUID garageId) {
        //TODO
        return null;
    }
}
