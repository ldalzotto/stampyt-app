package com.stampyt.controller;

import com.stampyt.controller.converter.CarBO2DTO;
import com.stampyt.controller.converter.CarDTO2BO;
import com.stampyt.controller.model.CarDTO;
import com.stampyt.service.CarService;
import com.stampyt.service.exceptions.NoCarFoundForGarage;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.UUID;

public class CarControllerTest {

    private static CarController carController;

    @BeforeClass
    public static void init() {

        CarService carService = Mockito.mock(CarService.class);

        try {
            Mockito.when(carService.getCar(Mockito.any())).thenThrow(SQLException.class);
            Mockito.when(carService.addCar(Mockito.any(), Mockito.any())).thenThrow(SQLException.class);
            Mockito.when(carService.deleteAllCars(Mockito.any())).thenThrow(SQLException.class);
            Mockito.when(carService.deleteCar(Mockito.any())).thenThrow(SQLException.class);
            Mockito.when(carService.getAllCars(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenThrow(SQLException.class);
            Mockito.when(carService.getCarNumber(Mockito.any())).thenThrow(SQLException.class);
            Mockito.when(carService.updateCarDetails(Mockito.any(), Mockito.any())).thenThrow(SQLException.class);
        } catch (NoCarFoundForGarage noCarFoundForGarage) {
            throw new RuntimeException(noCarFoundForGarage);
        }

        carController = new CarController(new CarDTO2BO(), new CarBO2DTO(), carService);

    }

    @Test(expected = SQLException.class)
    public void errorsNotCatchedByController_1() {
        carController.createCar(UUID.randomUUID().toString(), new CarDTO());
    }

    @Test(expected = SQLException.class)
    public void errorsNotCatchedByController_2() {
        carController.deleteAllCar(UUID.randomUUID().toString());
    }

    @Test(expected = SQLException.class)
    public void errorsNotCatchedByController_3() {
        carController.deleteCar(UUID.randomUUID().toString());
    }

    @Test(expected = SQLException.class)
    public void errorsNotCatchedByController_4() {
        carController.getAllCar(UUID.randomUUID().toString(), null, null, null);
    }

    @Test(expected = SQLException.class)
    public void errorsNotCatchedByController_5() {
        carController.getCar(UUID.randomUUID().toString());
    }

    @Test(expected = SQLException.class)
    public void errorsNotCatchedByController_6() {
        carController.updateCar(UUID.randomUUID().toString(), new CarDTO());
    }

}
