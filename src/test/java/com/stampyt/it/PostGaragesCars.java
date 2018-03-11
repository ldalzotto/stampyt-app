package com.stampyt.it;

import com.stampyt.Application;
import com.stampyt.EnvironmentVariableInitializer;
import com.stampyt.controller.constants.ResourcesConstants;
import com.stampyt.controller.handler.ExceptionMessage;
import com.stampyt.controller.handler.RestErrorHandler;
import com.stampyt.controller.model.CarDTO;
import com.stampyt.controller.model.GarageDTO;
import com.stampyt.it.jdd.GarageDTOProvider;
import com.stampyt.it.jdd.JDDAsserter;
import com.stampyt.it.jdd.URIRessourceProvider;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostGaragesCars {

    @BeforeClass
    public static void initEnv() throws Exception {
        EnvironmentVariableInitializer.initEnvironmentVariables();
    }

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void createCar_withMaxLimitAlreadyReached() {

        GarageDTO insertedGarage = GarageDTOProvider.insertRandomGarage(true, 3, 2, testRestTemplate).getBody();
        UUID garageId = insertedGarage.getGarageId();

        CarDTO carToInsert =
                GarageDTOProvider.generateRandomCar();

        this.testRestTemplate.postForEntity(URIRessourceProvider.buildGarageBasePath(garageId.toString()) + ResourcesConstants.PATH_CAR,
                carToInsert, CarDTO.class);

        ResponseEntity<ExceptionMessage> response =
                this.testRestTemplate.postForEntity(URIRessourceProvider.buildGarageBasePath(garageId.toString()) + ResourcesConstants.PATH_CAR,
                        carToInsert, ExceptionMessage.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        JDDAsserter.assertException(response.getBody(), null, RestErrorHandler.BAD_REQUEST);

    }

    @Test
    public void createCar_withGarageWithoutMaxCapacity() {

        GarageDTO generatedGarage = GarageDTOProvider.generateGarage(false);
        generatedGarage.setMaxCapacity(null);
        GarageDTO insertedGarage = GarageDTOProvider.insertGarage(generatedGarage, testRestTemplate).getBody();
        UUID garageId = insertedGarage.getGarageId();

        CarDTO carToInsert =
                GarageDTOProvider.generateRandomCar();

        ResponseEntity<ExceptionMessage> response =
                this.testRestTemplate.postForEntity(URIRessourceProvider.buildGarageBasePath(garageId.toString()) + ResourcesConstants.PATH_CAR,
                        carToInsert, ExceptionMessage.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        JDDAsserter.assertException(response.getBody(), null, RestErrorHandler.GARAGE_MAX_CAPACITY_UNDEFINED);

    }

    @Test
    public void createCar_withIncorrectprice() {

        GarageDTO insertedGarage = GarageDTOProvider.insertRandomGarage(true, testRestTemplate).getBody();
        UUID garageId = insertedGarage.getGarageId();

        CarDTO carToInsert =
                GarageDTOProvider.generateRandomCar();
        carToInsert.setPrice(-5F);

        ResponseEntity<ExceptionMessage> response =
                this.testRestTemplate.postForEntity(URIRessourceProvider.buildGarageBasePath(garageId.toString()) + ResourcesConstants.PATH_CAR,
                        carToInsert, ExceptionMessage.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        JDDAsserter.assertException(response.getBody(), null, RestErrorHandler.INVALID_FORMAT);

    }

}
