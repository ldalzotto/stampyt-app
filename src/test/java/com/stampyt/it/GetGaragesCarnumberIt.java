package com.stampyt.it;

import com.stampyt.Application;
import com.stampyt.EnvironmentVariableInitializer;
import com.stampyt.controller.constants.ResourcesConstants;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GetGaragesCarnumberIt {


    @BeforeClass
    public static void initEnv() throws Exception {
        EnvironmentVariableInitializer.initEnvironmentVariables();
    }

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void getCarNumber_unknownId() {

        ResponseEntity<ExceptionMessage> response =
                this.testRestTemplate.getForEntity(URIRessourceProvider.buildGarageBasePath(UUID.randomUUID().toString()) + ResourcesConstants.CAR_NUMBER_RESSOURCE,
                        ExceptionMessage.class);

        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        JDDAsserter.assertException(response.getBody(), null, RestErrorHandler.CAR_NOT_FOUND);
    }


    @Test
    public void getCarNumber_noCar() {

        GarageDTO insertedGarage = GarageDTOProvider.insertRandomGarage(false, testRestTemplate).getBody();
        UUID garageId = insertedGarage.getGarageId();

        ResponseEntity<ExceptionMessage> response =
                this.testRestTemplate.getForEntity(URIRessourceProvider.buildGarageBasePath(garageId.toString()) + ResourcesConstants.CAR_NUMBER_RESSOURCE,
                        ExceptionMessage.class);

        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        JDDAsserter.assertException(response.getBody(), null, RestErrorHandler.CAR_NOT_FOUND);
    }

}
