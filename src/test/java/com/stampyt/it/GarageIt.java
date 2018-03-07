package com.stampyt.it;

import com.stampyt.EnvironmentVariableInitializer;
import com.stampyt.hello.Application;
import com.stampyt.hello.controller.model.CarDTO;
import com.stampyt.hello.controller.model.GarageDTO;
import com.stampyt.it.jdd.GarageDTOProvider;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
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
        ResponseEntity<GarageDTO> response = testRestTemplate.postForEntity("/garage", garageDTO, GarageDTO.class);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        GarageDTO responseGarage = response.getBody();

        assertGarageDetails(garageDTO, responseGarage, timeBeforeApiCall);

    }

    @Test
    public void createGarageWithCars_nominalTest() {

        DateTime timeBeforeApiCall = DateTime.now(DateTimeZone.UTC);

        GarageDTO garageDTO = GarageDTOProvider.generateGarage(true);
        ResponseEntity<GarageDTO> response = testRestTemplate.postForEntity("/garage", garageDTO, GarageDTO.class);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        GarageDTO responseGarage = response.getBody();

        assertGarageDetails(garageDTO, responseGarage, timeBeforeApiCall);

    }

    @Test
    public void getGarage_nominalTest() {
        DateTime timeBeforeApiCall = DateTime.now(DateTimeZone.UTC);
        GarageDTO garageDTO = GarageDTOProvider.generateGarage(true);
        UUID garageId = testRestTemplate.postForEntity("/garage", garageDTO, GarageDTO.class).getBody().getGarageId();
        ResponseEntity<GarageDTO> response = testRestTemplate.getForEntity("/garage/" + garageId.toString(), GarageDTO.class);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        GarageDTO responseGarage = response.getBody();
        Assert.assertNotNull(responseGarage);
        Assert.assertEquals(responseGarage.getGarageId(), garageId);
        assertGarageDetails(garageDTO, responseGarage, timeBeforeApiCall);
    }

    @Test
    public void deleteCar_nominalTest() {
        UUID insertedGarageId = this.insertRandomGarage(true).getBody().getGarageId();
        testRestTemplate.delete("/garage/" + insertedGarageId.toString());
        ResponseEntity<GarageDTO> responseAfterDelete = testRestTemplate.getForEntity("/garage/" + insertedGarageId.toString(), GarageDTO.class);
        Assert.assertEquals(responseAfterDelete.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    private void assertGarageDetails(GarageDTO inputGarage, GarageDTO responseGarage, DateTime timeBeforeApiCall) {
        Assert.assertNotNull(responseGarage);
        Assert.assertEquals(responseGarage.getAddress(), inputGarage.getAddress());
        Assert.assertEquals(responseGarage.getMaxCapacity(), inputGarage.getMaxCapacity());
        Assert.assertEquals(responseGarage.getName(), inputGarage.getName());
        Assert.assertTrue(responseGarage.getCreationDate().compareTo(timeBeforeApiCall) == 0 ||
                responseGarage.getCreationDate().compareTo(timeBeforeApiCall) > 0);
        Assert.assertEquals(responseGarage.getCreationDate().getZone(), DateTimeZone.UTC);
        Assert.assertNotNull(responseGarage.getGarageId().toString());
        if (inputGarage.getCars() != null && inputGarage.getCars().size() > 0) {
            Assert.assertEquals(inputGarage.getCars().size(), responseGarage.getCars().size());
            for (CarDTO inputCars :
                    inputGarage.getCars()) {
                for (CarDTO outputCars :
                        responseGarage.getCars()) {
                    if (inputCars.getRegistrationNumber().equals(outputCars.getRegistrationNumber())) {
                        this.assertCarDetails(inputCars, outputCars);
                    }
                }
            }
        }
    }

    private void assertCarDetails(CarDTO inputCar, CarDTO outputCar) {
        Assert.assertEquals(inputCar.getBrand(), outputCar.getBrand());
        Assert.assertEquals(inputCar.getColor(), outputCar.getColor());
        Assert.assertEquals(inputCar.getModel(), outputCar.getModel());
        Assert.assertEquals(inputCar.getPrice(), outputCar.getPrice());
        Assert.assertEquals(inputCar.getCommisioningDate(), outputCar.getCommisioningDate());
        Assert.assertEquals(inputCar.getRegistrationNumber(), outputCar.getRegistrationNumber());
    }

    private ResponseEntity<GarageDTO> insertRandomGarage(boolean withCars) {
        GarageDTO garageDTO = GarageDTOProvider.generateGarage(withCars);
        return testRestTemplate.postForEntity("/garage", garageDTO, GarageDTO.class);
    }

}
