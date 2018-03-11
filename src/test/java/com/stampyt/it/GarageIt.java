package com.stampyt.it;

import com.stampyt.Application;
import com.stampyt.EnvironmentVariableInitializer;
import com.stampyt.controller.model.GarageDTO;
import com.stampyt.it.jdd.GarageDTOProvider;
import com.stampyt.it.jdd.JDDAsserter;
import com.stampyt.it.jdd.URIRessourceProvider;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
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
public class GarageIt {

    @BeforeClass
    public static void initEnv() throws Exception {
        EnvironmentVariableInitializer.initEnvironmentVariables();
    }

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void createGarageOnly_nominalTest() {

        DateTime timeBeforeApiCall = DateTime.now(DateTimeZone.UTC);

        GarageDTO garageDTO = GarageDTOProvider.generateGarage(false);
        ResponseEntity<GarageDTO> response = testRestTemplate.postForEntity(URIRessourceProvider.buildGarageBasePath(), garageDTO, GarageDTO.class);

        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        GarageDTO responseGarage = response.getBody();

        JDDAsserter.assertGarageDetails(garageDTO, responseGarage, timeBeforeApiCall);

    }

    @Test
    public void createGarageWithCars_nominalTest() {

        DateTime timeBeforeApiCall = DateTime.now(DateTimeZone.UTC);

        GarageDTO garageDTO = GarageDTOProvider.generateGarage(true);
        ResponseEntity<GarageDTO> response = testRestTemplate.postForEntity(URIRessourceProvider.buildGarageBasePath(), garageDTO, GarageDTO.class);

        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        GarageDTO responseGarage = response.getBody();

        JDDAsserter.assertGarageDetails(garageDTO, responseGarage, timeBeforeApiCall);

    }

    @Test
    public void getGarage_nominalTest() {
        DateTime timeBeforeApiCall = DateTime.now(DateTimeZone.UTC);
        GarageDTO garageDTO = GarageDTOProvider.generateGarage(true);
        UUID garageId = testRestTemplate.postForEntity(URIRessourceProvider.buildGarageBasePath(), garageDTO, GarageDTO.class).getBody().getGarageId();
        ResponseEntity<GarageDTO> response = testRestTemplate.getForEntity(URIRessourceProvider.buildGarageBasePath(garageId.toString()), GarageDTO.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        GarageDTO responseGarage = response.getBody();
        Assert.assertNotNull(responseGarage);
        Assert.assertEquals(responseGarage.getGarageId(), garageId);
        JDDAsserter.assertGarageDetails(garageDTO, responseGarage, timeBeforeApiCall);
    }

    @Test
    public void deleteCar_nominalTest() {
        UUID insertedGarageId = this.insertRandomGarage(true).getBody().getGarageId();
        testRestTemplate.delete(URIRessourceProvider.buildGarageBasePath(insertedGarageId.toString()));

        ResponseEntity<GarageDTO> responseAfterDelete = testRestTemplate.getForEntity(URIRessourceProvider.buildGarageBasePath(insertedGarageId.toString()), GarageDTO.class);

        Assert.assertEquals(HttpStatus.NOT_FOUND, responseAfterDelete.getStatusCode());
    }

    @Test
    public void updateGarageDetails_nominalTest() {
        DateTime timeBeforeApiCall = DateTime.now(DateTimeZone.UTC);

        GarageDTO generatedGarage = this.insertRandomGarage(true).getBody();
        UUID insertedGarageId = generatedGarage.getGarageId();

        GarageDTO garageDetailsToUpdate = GarageDTOProvider.generateGarageDetails("AnotherName", null, null);

        ResponseEntity<Void> response =
                testRestTemplate.exchange(URIRessourceProvider.buildGarageBasePath(insertedGarageId.toString()), HttpMethod.PUT, new HttpEntity<>(garageDetailsToUpdate),
                        Void.class);
        Assert.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Assert.assertNull(response.getBody());

        ResponseEntity<GarageDTO> updatedGarageResponse = testRestTemplate.getForEntity(URIRessourceProvider.buildGarageBasePath(insertedGarageId.toString()), GarageDTO.class);

        Assert.assertEquals(HttpStatus.OK, updatedGarageResponse.getStatusCode());

        generatedGarage.setName("AnotherName");
        JDDAsserter.assertGarageDetails(generatedGarage, updatedGarageResponse.getBody(), timeBeforeApiCall);
    }

    private ResponseEntity<GarageDTO> insertRandomGarage(boolean withCars) {
        GarageDTO garageDTO = GarageDTOProvider.generateGarage(withCars);
        return testRestTemplate.postForEntity(URIRessourceProvider.buildGarageBasePath(), garageDTO, GarageDTO.class);
    }


}
