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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostGaragesIt {

    @BeforeClass
    public static void initEnv() throws Exception {
        EnvironmentVariableInitializer.initEnvironmentVariables();
    }

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void garageCreationWithCars_withoutMaxCapacity() {

        GarageDTO garageDTOToInsert = GarageDTOProvider.generateGarage(true, 3);
        garageDTOToInsert.setMaxCapacity(null);

        ResponseEntity<GarageDTO> response =
                this.testRestTemplate.postForEntity(URIRessourceProvider.buildGarageBasePath(), garageDTOToInsert, GarageDTO.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void garageCreationWithCars_incorrectMaxCapacity() {

        GarageDTO garageDTOToInsert = GarageDTOProvider.generateGarage(true, 3);
        garageDTOToInsert.setMaxCapacity(-2);

        ResponseEntity<ExceptionMessage> response =
                this.testRestTemplate.postForEntity(URIRessourceProvider.buildGarageBasePath(), garageDTOToInsert, ExceptionMessage.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        JDDAsserter.assertException(response.getBody(), null, RestErrorHandler.INVALID_FORMAT);
    }

    @Test
    public void garageCreationWithCars_incorrectPrice() {
        GarageDTO garageDTOToInsert = GarageDTOProvider.generateGarage(true, 3);
        garageDTOToInsert.getCars().get(0).setPrice(-1F);

        ResponseEntity<ExceptionMessage> response =
                this.testRestTemplate.postForEntity(URIRessourceProvider.buildGarageBasePath(), garageDTOToInsert, ExceptionMessage.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        JDDAsserter.assertException(response.getBody(), null, RestErrorHandler.INVALID_FORMAT);
    }

    @Test
    public void garageCreationWithCars_tooMuchCars() {
        GarageDTO garageDTOToInsert = GarageDTOProvider.generateGarage(true, 10);
        garageDTOToInsert.setMaxCapacity(5);

        ResponseEntity<ExceptionMessage> response =
                this.testRestTemplate.postForEntity(URIRessourceProvider.buildGarageBasePath(), garageDTOToInsert, ExceptionMessage.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        JDDAsserter.assertException(response.getBody(), null, RestErrorHandler.BAD_REQUEST);
    }

}
