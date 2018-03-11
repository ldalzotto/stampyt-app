package com.stampyt.it;

import com.stampyt.Application;
import com.stampyt.EnvironmentVariableInitializer;
import com.stampyt.controller.handler.ExceptionMessage;
import com.stampyt.controller.handler.RestErrorHandler;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PutGragesIt {

    @BeforeClass
    public static void initEnv() throws Exception {
        EnvironmentVariableInitializer.initEnvironmentVariables();
    }

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void modifyGarage_withInvalidMaxCapacity() {
        GarageDTO insertedGarage = GarageDTOProvider.insertRandomGarage(true, this.testRestTemplate).getBody();
        UUID garageId = insertedGarage.getGarageId();

        GarageDTO valuesToChange = new GarageDTO();
        valuesToChange.setMaxCapacity(-2);

        ResponseEntity<ExceptionMessage> response =
                this.testRestTemplate.exchange(URIRessourceProvider.buildGarageBasePath(garageId.toString()), HttpMethod.PUT, new HttpEntity<>(valuesToChange),
                        ExceptionMessage.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        JDDAsserter.assertException(response.getBody(), null, RestErrorHandler.INVALID_FORMAT);
    }

    @Test
    public void modifyGarage_withCarModification() {
        GarageDTO insertedGarage = GarageDTOProvider.insertRandomGarage(true, this.testRestTemplate).getBody();
        UUID garageId = insertedGarage.getGarageId();

        GarageDTO valuesToChange = new GarageDTO();
        valuesToChange.setCars(Arrays.asList(GarageDTOProvider.generateRandomCar()));

        ResponseEntity<ExceptionMessage> response =
                this.testRestTemplate.exchange(URIRessourceProvider.buildGarageBasePath(garageId.toString()), HttpMethod.PUT, new HttpEntity<>(valuesToChange),
                        ExceptionMessage.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        JDDAsserter.assertException(response.getBody(), null, RestErrorHandler.BAD_REQUEST);
    }

    @Test
    public void modifyGarage_unknwonGarage() {
        GarageDTO generatedGarage = GarageDTOProvider.generateGarage(false);

        ResponseEntity<ExceptionMessage> response =
                this.testRestTemplate.exchange(URIRessourceProvider.buildGarageBasePath(UUID.randomUUID().toString()), HttpMethod.PUT, new HttpEntity<>(generatedGarage),
                        ExceptionMessage.class);

        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        JDDAsserter.assertException(response.getBody(), null, RestErrorHandler.GARAGE_NOT_FOUND);
    }

}
