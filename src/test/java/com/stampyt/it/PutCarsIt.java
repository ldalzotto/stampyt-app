package com.stampyt.it;

import com.stampyt.EnvironmentVariableInitializer;
import com.stampyt.hello.Application;
import com.stampyt.hello.controller.handler.ExceptionMessage;
import com.stampyt.hello.controller.handler.RestErrorHandler;
import com.stampyt.hello.controller.model.CarDTO;
import com.stampyt.hello.controller.model.GarageDTO;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PutCarsIt {


    @BeforeClass
    public static void initEnv() throws Exception {
        EnvironmentVariableInitializer.initEnvironmentVariables();
    }

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void modifyCar_notAllowedModification() {
        GarageDTO garageDTO = GarageDTOProvider.insertRandomGarage(true, testRestTemplate).getBody();
        UUID garageId = garageDTO.getGarageId();

        CarDTO generatedCar = GarageDTOProvider.generateRandomCar();
        CarDTO createdCar = GarageDTOProvider.saveCar(generatedCar, garageId, testRestTemplate).getBody();
        UUID carId = createdCar.getCardId();

        CarDTO carValuesToSave = new CarDTO();
        carValuesToSave.setBrand("TEST");

        ResponseEntity<ExceptionMessage> response =
                this.testRestTemplate.exchange(URIRessourceProvider.buildCarBasePath(carId.toString()), HttpMethod.PUT, new HttpEntity<>(carValuesToSave),
                        ExceptionMessage.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    @Test
    public void modifyCar_unknwonCarId() {

        GarageDTOProvider.insertRandomGarage(true, testRestTemplate);

        CarDTO generatedCar = new CarDTO();

        ResponseEntity<ExceptionMessage> response =
                this.testRestTemplate.exchange(URIRessourceProvider.buildCarBasePath(UUID.randomUUID().toString()), HttpMethod.PUT, new HttpEntity<>(generatedCar),
                        ExceptionMessage.class);
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        JDDAsserter.assertException(response.getBody(), null, RestErrorHandler.CAR_NOT_FOUND);

    }

}
