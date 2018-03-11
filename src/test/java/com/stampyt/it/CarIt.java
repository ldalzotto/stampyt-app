package com.stampyt.it;

import com.stampyt.EnvironmentVariableInitializer;
import com.stampyt.hello.Application;
import com.stampyt.hello.controller.constants.ResourcesConstants;
import com.stampyt.hello.controller.model.CarDTO;
import com.stampyt.hello.controller.model.CarsDTO;
import com.stampyt.hello.controller.model.GarageCarNumberDTO;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarIt {

    @BeforeClass
    public static void initEnv() throws Exception {
        EnvironmentVariableInitializer.initEnvironmentVariables();
    }

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void getCarNumber_nominalTest() {
        GarageDTO insertedGarage = this.generateGarage(true, 5);
        UUID garageId = insertedGarage.getGarageId();
        ResponseEntity<GarageCarNumberDTO> garageCarNumberDTO =
                this.testRestTemplate.getForEntity(URIRessourceProvider.buildGarageBasePath(garageId.toString()) + ResourcesConstants.CAR_NUMBER_RESSOURCE, GarageCarNumberDTO.class);
        Assert.assertEquals(HttpStatus.OK, garageCarNumberDTO.getStatusCode());

        GarageCarNumberDTO awaitedResult = new GarageCarNumberDTO();
        awaitedResult.setGarageId(garageId);
        awaitedResult.setCarNumber(5);

        JDDAsserter.assertCarNumberDTO(awaitedResult, garageCarNumberDTO.getBody());
    }

    @Test
    public void addCarToGarage_nominalTest() {
        GarageDTO insertedGarage = this.generateGarage(false, null);
        insertedGarage.setMaxCapacity(8);
        UUID garageId = insertedGarage.getGarageId();
        CarDTO randomCar = this.generateCar();

        ResponseEntity<CarDTO> savedCar =
                this.testRestTemplate.postForEntity(URIRessourceProvider.buildGarageBasePath(garageId.toString()) + ResourcesConstants.PATH_CAR, randomCar, CarDTO.class);
        Assert.assertEquals(HttpStatus.CREATED, savedCar.getStatusCode());
        JDDAsserter.assertCarDetails(randomCar, savedCar.getBody());
        Assert.assertNotNull(savedCar.getBody().getCardId());
    }

    @Test
    public void getCarDetail_nominalTest() {
        CarDTO savedCar = this.saveRandomCar(8);
        UUID carId = savedCar.getCardId();
        ResponseEntity<CarDTO> recoveredCar =
                this.testRestTemplate.getForEntity(URIRessourceProvider.buildCarBasePath(carId.toString()), CarDTO.class);
        Assert.assertEquals(HttpStatus.OK, recoveredCar.getStatusCode());
        JDDAsserter.assertCarDetails(recoveredCar.getBody(), savedCar);
    }

    @Test
    public void deleteCar_nominalTest() {
        CarDTO savedCar = this.saveRandomCar(8);
        UUID carId = savedCar.getCardId();
        this.testRestTemplate.delete(URIRessourceProvider.buildCarBasePath(carId.toString()));
        ResponseEntity<CarDTO> recoveredCar =
                this.testRestTemplate.getForEntity(URIRessourceProvider.buildCarBasePath(carId.toString()), CarDTO.class);
        Assert.assertEquals(HttpStatus.NOT_FOUND, recoveredCar.getStatusCode());
    }

    @Test
    public void updateCar_nominalTest() {
        CarDTO savedCar = this.saveRandomCar(8);
        UUID carId = savedCar.getCardId();

        CarDTO modifiedValues = new CarDTO();
        modifiedValues.setRegistrationNumber("MyNumber");

        ResponseEntity<Void> response =
                this.testRestTemplate.exchange(URIRessourceProvider.buildCarBasePath(carId.toString()), HttpMethod.PUT, new HttpEntity<>(modifiedValues), Void.class);

        Assert.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Assert.assertNull(response.getBody());

        savedCar.setRegistrationNumber("MyNumber");
        ResponseEntity<CarDTO> recoveredCar =
                this.testRestTemplate.getForEntity(URIRessourceProvider.buildCarBasePath(carId.toString()), CarDTO.class);

        JDDAsserter.assertCarDetails(recoveredCar.getBody(), savedCar);

    }

    @Test
    public void getAllCars_nominalTest() {
        GarageDTO insertedGarage = this.generateGarage(true, 5);
        UUID garageId = insertedGarage.getGarageId();
        ResponseEntity<CarsDTO> cars =
                this.testRestTemplate.getForEntity(URIRessourceProvider.buildGarageBasePath(garageId.toString()) + ResourcesConstants.CAR_ALL_RESSOURCE, CarsDTO.class);
        Assert.assertEquals(HttpStatus.OK, cars.getStatusCode());
        JDDAsserter.assertCarsDetails(cars.getBody().getCars(), insertedGarage.getCars());
    }

    @Test
    public void getAllCarsWithFilter_nominalTest() {
        GarageDTO insertedGarage = this.generateGarage(true, 15, Arrays.asList("blue", "red"), 50f, 100f);
        UUID garageId = insertedGarage.getGarageId();

        //getting blue cars between 50 and 75;
        ResponseEntity<CarsDTO> cars =
                this.testRestTemplate
                        .getForEntity(URIRessourceProvider.buildGarageBasePath(garageId.toString()) + ResourcesConstants.CAR_ALL_RESSOURCE
                                + "?color=red&minPrice=50&maxPrice=75", CarsDTO.class);

        Assert.assertEquals(HttpStatus.OK, cars.getStatusCode());

        CarsDTO insertedCars = new CarsDTO();
        insertedCars.setCars(insertedGarage.getCars());
        GetCarsFilterAsserter.assertCars(cars.getBody(), insertedCars, "red", 50f, 75f);

    }

    @Test
    public void deleteAllCars_nominalTest() {
        GarageDTO insertedGarage = this.generateGarage(true, 5);
        UUID garageId = insertedGarage.getGarageId();
        this.testRestTemplate.delete(URIRessourceProvider.buildGarageBasePath(garageId.toString()) + ResourcesConstants.CAR_ALL_RESSOURCE);
        ResponseEntity<CarsDTO> cars =
                this.testRestTemplate.getForEntity(URIRessourceProvider.buildGarageBasePath(garageId.toString()) + ResourcesConstants.CAR_ALL_RESSOURCE, CarsDTO.class);
        Assert.assertEquals(HttpStatus.OK, cars.getStatusCode());
        Assert.assertEquals(cars.getBody().getCars().size(), 0);
    }


    private GarageDTO generateGarage(boolean withCar, Integer carNb, List<String> colorChoice, Float minPrice, Float maxPrice) {
        GarageDTO genereatedGarage = GarageDTOProvider.generateGarage(withCar, carNb, colorChoice, minPrice, maxPrice);
        ResponseEntity<GarageDTO> insertedGarage = testRestTemplate.postForEntity(URIRessourceProvider.buildGarageBasePath(), genereatedGarage, GarageDTO.class);
        return insertedGarage.getBody();
    }

    private GarageDTO generateGarage(boolean withCar, Integer carNb) {
        GarageDTO genereatedGarage = GarageDTOProvider.generateGarage(withCar, carNb);
        ResponseEntity<GarageDTO> insertedGarage = testRestTemplate.postForEntity(URIRessourceProvider.buildGarageBasePath(), genereatedGarage, GarageDTO.class);
        return insertedGarage.getBody();
    }

    private CarDTO generateCar() {
        return GarageDTOProvider.generateRandomCar();
    }

    private CarDTO saveRandomCar(Integer maxCapacity) {
        GarageDTO insertedGarage = this.generateGarage(false, null);
        insertedGarage.setMaxCapacity(maxCapacity);
        UUID garageId = insertedGarage.getGarageId();
        CarDTO randomCar = this.generateCar();
        ResponseEntity<CarDTO> savedCar =
                this.testRestTemplate.postForEntity(URIRessourceProvider.buildGarageBasePath(garageId.toString()) + ResourcesConstants.PATH_CAR, randomCar, CarDTO.class);
        return savedCar.getBody();
    }

}
