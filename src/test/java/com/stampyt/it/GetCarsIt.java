package com.stampyt.it;

import com.stampyt.EnvironmentVariableInitializer;
import com.stampyt.hello.Application;
import com.stampyt.hello.controller.constants.ResourcesConstants;
import com.stampyt.hello.controller.handler.ExceptionMessage;
import com.stampyt.hello.controller.handler.RestErrorHandler;
import com.stampyt.hello.controller.model.CarsDTO;
import com.stampyt.hello.controller.model.GarageDTO;
import com.stampyt.it.jdd.GarageDTOProvider;
import com.stampyt.it.jdd.GetCarsFilterAsserter;
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

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GetCarsIt {

    @BeforeClass
    public static void initEnv() throws Exception {
        EnvironmentVariableInitializer.initEnvironmentVariables();
    }

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void getCars_withMalformedQuery() {
        GarageDTO insertedGarage = this.generateGarage(true, 15, Arrays.asList("blue", "red"), 50f, 100f);
        UUID garageId = insertedGarage.getGarageId();

        //getting blue cars between 50 and 75;
        ResponseEntity<ExceptionMessage> response =
                this.testRestTemplate
                        .getForEntity(URIRessourceProvider.buildGarageBasePath(garageId.toString()) + ResourcesConstants.CAR_ALL_RESSOURCE
                                + "?color=535353&minPrice=gegzrg&maxPrice=gegzrg", ExceptionMessage.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        JDDAsserter.assertException(response.getBody(), null, RestErrorHandler.INVALID_FORMAT);
    }

    @Test
    public void getCars_colorOnly() {
        GarageDTO insertedGarage = this.generateGarage(true, 15, Arrays.asList("blue", "red"), 50f, 100f);
        UUID garageId = insertedGarage.getGarageId();

        //getting blue cars between 50 and 75;
        ResponseEntity<CarsDTO> response =
                this.testRestTemplate
                        .getForEntity(URIRessourceProvider.buildGarageBasePath(garageId.toString()) + ResourcesConstants.CAR_ALL_RESSOURCE
                                + "?color=red", CarsDTO.class);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        CarsDTO inputCars = new CarsDTO();
        inputCars.setCars(insertedGarage.getCars());
        GetCarsFilterAsserter.assertCars(response.getBody(), inputCars, "red", null, null);
    }

    @Test
    public void getCars_minPriceOnly() {
        GarageDTO insertedGarage = this.generateGarage(true, 15, Arrays.asList("blue", "red"), 50f, 100f);
        UUID garageId = insertedGarage.getGarageId();

        //getting blue cars between 50 and 75;
        ResponseEntity<CarsDTO> response =
                this.testRestTemplate
                        .getForEntity(URIRessourceProvider.buildGarageBasePath(garageId.toString()) + ResourcesConstants.CAR_ALL_RESSOURCE
                                + "?minPrice=75", CarsDTO.class);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        CarsDTO inputCars = new CarsDTO();
        inputCars.setCars(insertedGarage.getCars());
        GetCarsFilterAsserter.assertCars(response.getBody(), inputCars, null, 75f, null);
    }

    @Test
    public void getCars_maxPriceOnly() {
        GarageDTO insertedGarage = this.generateGarage(true, 15, Arrays.asList("blue", "red"), 50f, 100f);
        UUID garageId = insertedGarage.getGarageId();

        //getting blue cars between 50 and 75;
        ResponseEntity<CarsDTO> response =
                this.testRestTemplate
                        .getForEntity(URIRessourceProvider.buildGarageBasePath(garageId.toString()) + ResourcesConstants.CAR_ALL_RESSOURCE
                                + "?maxPrice=75", CarsDTO.class);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        CarsDTO inputCars = new CarsDTO();
        inputCars.setCars(insertedGarage.getCars());
        GetCarsFilterAsserter.assertCars(response.getBody(), inputCars, null, null, 75f);
    }


    private GarageDTO generateGarage(boolean withCar, Integer carNb, List<String> colorChoice, Float minPrice, Float maxPrice) {
        GarageDTO genereatedGarage = GarageDTOProvider.generateGarage(withCar, carNb, colorChoice, minPrice, maxPrice);
        ResponseEntity<GarageDTO> insertedGarage = testRestTemplate.postForEntity(URIRessourceProvider.buildGarageBasePath(), genereatedGarage, GarageDTO.class);
        return insertedGarage.getBody();
    }

}
