package com.stampyt.it.jdd;

import com.stampyt.hello.controller.handler.ExceptionMessage;
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
            assertCarsDetails(responseGarage.getCars(), inputGarage.getCars());
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
        Assert.assertEquals(outputCar.getBrand(), inputCar.getBrand());
        Assert.assertEquals(outputCar.getColor(), inputCar.getColor());
        Assert.assertEquals(outputCar.getModel(), inputCar.getModel());
        Assert.assertEquals(outputCar.getPrice(), inputCar.getPrice());
        Assert.assertEquals(outputCar.getCommisioningDate(), inputCar.getCommisioningDate());
        Assert.assertEquals(outputCar.getRegistrationNumber(), inputCar.getRegistrationNumber());
    }

    public static void assertCarNumberDTO(GarageCarNumberDTO inputCarNumber, GarageCarNumberDTO outputCarNumber) {
        Assert.assertEquals(outputCarNumber.getCarNumber(), inputCarNumber.getCarNumber());
        Assert.assertEquals(outputCarNumber.getGarageId(), inputCarNumber.getGarageId());
    }

    public static void assertException(ExceptionMessage actual, String expectedMessage, String expectedCode) {

        if (expectedMessage != null) {
            Assert.assertEquals(expectedMessage, actual.getErrorMessage());
        }
        if (expectedCode != null) {
            Assert.assertEquals(expectedCode, actual.getErrorCode());
        }
    }

}
