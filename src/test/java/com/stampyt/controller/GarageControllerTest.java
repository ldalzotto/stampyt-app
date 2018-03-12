package com.stampyt.controller;

import com.stampyt.controller.converter.CarBO2DTO;
import com.stampyt.controller.converter.CarDTO2BO;
import com.stampyt.controller.converter.GarageBO2DTO;
import com.stampyt.controller.converter.GarageDTO2BO;
import com.stampyt.controller.model.GarageDTO;
import com.stampyt.service.GarageService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.UUID;

public class GarageControllerTest {

    private static GarageController garageController;

    @BeforeClass
    public static void init() {
        GarageService garageService = Mockito.mock(GarageService.class);
        Mockito.when(garageService.createGarage(Mockito.any())).thenThrow(SQLException.class);
        Mockito.when(garageService.deleteGarage(Mockito.any())).thenThrow(SQLException.class);
        Mockito.when(garageService.getGarage(Mockito.any())).thenThrow(SQLException.class);
        Mockito.when(garageService.getGarageMaxCapacity(Mockito.any())).thenThrow(SQLException.class);
        Mockito.when(garageService.updateGarage(Mockito.any(), Mockito.any())).thenThrow(SQLException.class);

        garageController = new GarageController(garageService, new GarageDTO2BO(new CarDTO2BO()), new GarageBO2DTO(new CarBO2DTO()));

    }

    @Test(expected = SQLException.class)
    public void errorsNotCatchedByController_1() {
        garageController.createGarage(new GarageDTO());
    }

    @Test(expected = SQLException.class)
    public void errorsNotCatchedByController_2() {
        garageController.deleteGarage(UUID.randomUUID().toString());
    }

    @Test(expected = SQLException.class)
    public void errorsNotCatchedByController_3() {
        garageController.getGarage(UUID.randomUUID().toString());
    }

    @Test(expected = SQLException.class)
    public void errorsNotCatchedByController_4() {
        garageController.updateGarage(UUID.randomUUID().toString(), new GarageDTO());
    }
}
