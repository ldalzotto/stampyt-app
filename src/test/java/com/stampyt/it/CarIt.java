package com.stampyt.it;

import com.stampyt.EnvironmentVariableInitializer;
import com.stampyt.hello.Application;
import com.stampyt.hello.controller.constants.ResourcesConstants;
import com.stampyt.hello.controller.model.CarDTO;
import com.stampyt.hello.controller.model.CarsDTO;
import com.stampyt.hello.controller.model.GarageCarNumberDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

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
        Assert.assertEquals(garageCarNumberDTO.getStatusCode(), HttpStatus.OK);

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
        Assert.assertEquals(savedCar.getStatusCode(), HttpStatus.CREATED);
        JDDAsserter.assertCarDetails(randomCar, savedCar.getBody());
        Assert.assertNotNull(savedCar.getBody().getCardId());
    }

    @Test
    public void getCarDetail_nominalTest() {
        CarDTO savedCar = this.saveRandomCar(8);
        UUID carId = savedCar.getCardId();
        ResponseEntity<CarDTO> recoveredCar =
                this.testRestTemplate.getForEntity(URIRessourceProvider.buildCarBasePath(carId.toString()), CarDTO.class);
        Assert.assertEquals(recoveredCar.getStatusCode(), HttpStatus.OK);
        JDDAsserter.assertCarDetails(recoveredCar.getBody(), savedCar);
    }

    @Test
    public void deleteCar_nominalTest() {
        CarDTO savedCar = this.saveRandomCar(8);
        UUID carId = savedCar.getCardId();
        this.testRestTemplate.delete(URIRessourceProvider.buildCarBasePath(carId.toString()));
        ResponseEntity<CarDTO> recoveredCar =
                this.testRestTemplate.getForEntity(URIRessourceProvider.buildCarBasePath(carId.toString()), CarDTO.class);
        Assert.assertEquals(recoveredCar.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void updateCar_nominalTest() {
        CarDTO savedCar = this.saveRandomCar(8);
        UUID carId = savedCar.getCardId();

        CarDTO modifiedValues = new CarDTO();
        modifiedValues.setRegistrationNumber("MyNumber");

        this.testRestTemplate.put(URIRessourceProvider.buildCarBasePath(carId.toString()), modifiedValues, CarDTO.class);

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
        Assert.assertEquals(cars.getStatusCode(), HttpStatus.OK);
        JDDAsserter.assertCarsDetails(cars.getBody().getCars(), insertedGarage.getCars());
    }

    @Test
    public void deleteAllCars_nominalTest() {
        GarageDTO insertedGarage = this.generateGarage(true, 5);
        UUID garageId = insertedGarage.getGarageId();
        this.testRestTemplate.delete(URIRessourceProvider.buildGarageBasePath(garageId.toString()) + ResourcesConstants.CAR_ALL_RESSOURCE);
        ResponseEntity<CarsDTO> cars =
                this.testRestTemplate.getForEntity(URIRessourceProvider.buildGarageBasePath(garageId.toString()) + ResourcesConstants.CAR_ALL_RESSOURCE, CarsDTO.class);
        Assert.assertEquals(cars.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(cars.getBody().getCars().size(), 0);
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
