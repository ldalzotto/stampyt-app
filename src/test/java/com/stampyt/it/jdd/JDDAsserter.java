package com.stampyt.it.jdd;

import com.stampyt.hello.controller.model.CarDTO;
import com.stampyt.hello.controller.model.GarageCarNumberDTO;
import com.stampyt.hello.controller.model.GarageDTO;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Assert;

import java.util.List;

public class JDDAsserter {

    public static void assertGarageDetails(GarageDTO inputGarage, GarageDTO responseGarage, DateTime timeBeforeApiCall) {
        Assert.assertNotNull(responseGarage);
        Assert.assertEquals(responseGarage.getAddress(), inputGarage.getAddress());
        Assert.assertEquals(responseGarage.getMaxCapacity(), inputGarage.getMaxCapacity());
        Assert.assertEquals(responseGarage.getName(), inputGarage.getName());
        Assert.assertTrue(responseGarage.getCreationDate().compareTo(timeBeforeApiCall) == 0 ||
                responseGarage.getCreationDate().compareTo(timeBeforeApiCall) > 0);
        Assert.assertEquals(responseGarage.getCreationDate().getZone(), DateTimeZone.UTC);
        Assert.assertNotNull(responseGarage.getGarageId().toString());
        if (inputGarage.getCars() != null && inputGarage.getCars().size() > 0) {
            assertCarsDetails(inputGarage.getCars(), responseGarage.getCars());
        }
    }

    public static void assertCarsDetails(List<CarDTO> inputCars, List<CarDTO> responseCars) {
        Assert.assertEquals(inputCars.size(), responseCars.size());
        for (CarDTO inputCar :
                inputCars) {
            for (CarDTO outputCar :
                    responseCars) {
                inputCar.setCardId(outputCar.getCardId());
                if (inputCar.equals(outputCar)) {
                    JDDAsserter.assertCarDetails(inputCar, outputCar);
                }
            }
        }
    }

    public static void assertCarDetails(CarDTO inputCar, CarDTO outputCar) {
        Assert.assertEquals(inputCar.getBrand(), outputCar.getBrand());
        Assert.assertEquals(inputCar.getColor(), outputCar.getColor());
        Assert.assertEquals(inputCar.getModel(), outputCar.getModel());
        Assert.assertEquals(inputCar.getPrice(), outputCar.getPrice());
        Assert.assertEquals(inputCar.getCommisioningDate(), outputCar.getCommisioningDate());
        Assert.assertEquals(inputCar.getRegistrationNumber(), outputCar.getRegistrationNumber());
    }

    public static void assertCarNumberDTO(GarageCarNumberDTO inputCarNumber, GarageCarNumberDTO outputCarNumber) {
        Assert.assertEquals(inputCarNumber.getCarNumber(), outputCarNumber.getCarNumber());
        Assert.assertEquals(inputCarNumber.getGarageId(), outputCarNumber.getGarageId());
    }

}
