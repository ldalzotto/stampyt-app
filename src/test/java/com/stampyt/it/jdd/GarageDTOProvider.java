package com.stampyt.it.jdd;

import com.stampyt.hello.controller.model.CarDTO;
import com.stampyt.hello.controller.model.GarageDTO;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.ArrayList;
import java.util.List;

public class GarageDTOProvider {

    public static GarageDTO generateGarage(boolean withCars) {
        return GarageDTOProvider.generateGarage(withCars, null);
    }

    public static GarageDTO generateGarage(boolean withCars, Integer carNb) {
        GarageDTO garageDTO = generateRandomGarage();
        if (withCars) {
            if (carNb == null) {
                carNb = RandomUtils.nextInt(1, 10);
            }
            garageDTO = addRandomCars(garageDTO, carNb);
        }
        return garageDTO;
    }

    public static GarageDTO generateGarageDetails(String name, String address, Integer maxCapacity) {
        GarageDTO garageDTO = new GarageDTO();
        if (name != null) {
            garageDTO.setName(name);
        }
        if (address != null) {
            garageDTO.setAddress(address);
        }
        if (maxCapacity != null) {
            garageDTO.setMaxCapacity(maxCapacity);
        }
        return garageDTO;
    }

    private static GarageDTO generateRandomGarage() {
        GarageDTO garageDTO = new GarageDTO();
        garageDTO.setName(RandomStringUtils.random(10, true, false));
        garageDTO.setMaxCapacity(RandomUtils.nextInt(1, Integer.MAX_VALUE));
        garageDTO.setAddress(RandomStringUtils.random(10, true, false));
        return garageDTO;
    }

    private static GarageDTO addRandomCars(GarageDTO garageDTO, int carNb) {
        List<CarDTO> generatedCars = new ArrayList<>();

        for (int i = 0; i < carNb; i++) {
            generatedCars.add(generateRandomCar());
        }

        if (garageDTO != null) {
            garageDTO.setCars(generatedCars);
        }
        return garageDTO;
    }

    public static CarDTO generateRandomCar() {
        CarDTO carDTO = new CarDTO();
        carDTO.setPrice(RandomUtils.nextFloat(0.1F, Float.MAX_VALUE));
        carDTO.setModel(RandomStringUtils.random(10));
        carDTO.setColor(RandomStringUtils.random(10));
        carDTO.setBrand(RandomStringUtils.random(10));
        carDTO.setCommisioningDate(DateTime.now(DateTimeZone.UTC));
        carDTO.setRegistrationNumber(RandomStringUtils.random(10));
        return carDTO;
    }

}
