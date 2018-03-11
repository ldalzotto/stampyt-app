package com.stampyt.controller;

import com.stampyt.controller.constants.ResourcesConstants;
import com.stampyt.controller.converter.CarBO2DTO;
import com.stampyt.controller.converter.CarDTO2BO;
import com.stampyt.controller.model.CarDTO;
import com.stampyt.controller.model.CarsDTO;
import com.stampyt.controller.model.GarageCarNumberDTO;
import com.stampyt.controller.valiator.ValidationUtil;
import com.stampyt.service.CarService;
import com.stampyt.service.exceptions.NoCarFoundForGarage;
import com.stampyt.service.model.CarBO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;


@RestController
public class CarController {

    public CarController(CarDTO2BO carDTO2BO, CarBO2DTO carBO2DTO, CarService carService) {
        this.carDTO2BO = carDTO2BO;
        this.carBO2DTO = carBO2DTO;
        this.carService = carService;
    }

    private CarDTO2BO carDTO2BO;
    private CarBO2DTO carBO2DTO;
    private CarService carService;

    @RequestMapping(value = ResourcesConstants.PATH_GARAGE_WITH_GARAGE_ID + ResourcesConstants.CAR_NUMBER_RESSOURCE, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public GarageCarNumberDTO getCarNumber(@PathVariable(value = ResourcesConstants.GARAGE_ID_PATH_VARIABLE_NAME) String garageId) throws NoCarFoundForGarage {
        UUID validatedGarageId = ValidationUtil.validateIdFormat(garageId);
        Integer carNumber = this.carService.getCarNumber(validatedGarageId);
        GarageCarNumberDTO garageCarNumberDTO = new GarageCarNumberDTO();
        garageCarNumberDTO.setGarageId(validatedGarageId);
        garageCarNumberDTO.setCarNumber(carNumber);
        return garageCarNumberDTO;
    }

    @RequestMapping(value = ResourcesConstants.PATH_GARAGE_WITH_GARAGE_ID + ResourcesConstants.PATH_CAR, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public CarDTO createCar(@PathVariable(value = ResourcesConstants.GARAGE_ID_PATH_VARIABLE_NAME) String garageId,
                            @Valid @RequestBody CarDTO carDefinition) {
        UUID validatedGarageId = ValidationUtil.validateIdFormat(garageId);
        carDefinition.setCardId(validatedGarageId);
        CarBO carBO = this.carDTO2BO.convert(carDefinition);
        CarBO addedCar = this.carService.addCar(validatedGarageId, carBO);
        return this.carBO2DTO.convert(addedCar);
    }

    @RequestMapping(value = ResourcesConstants.PATH_CAR_WITH_CAR_ID, method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCar(@PathVariable(value = ResourcesConstants.CAR_ID_PATH_VARIABLE_NAME) String carId) {
        UUID validatedCarId = ValidationUtil.validateIdFormat(carId);
        this.carService.deleteCar(validatedCarId);
    }

    @RequestMapping(value = ResourcesConstants.PATH_CAR_WITH_CAR_ID, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CarDTO getCar(@PathVariable(value = ResourcesConstants.CAR_ID_PATH_VARIABLE_NAME) String carId) {
        UUID validatedCarId = ValidationUtil.validateIdFormat(carId);
        CarBO retrievedCar = this.carService.getCar(validatedCarId);
        return this.carBO2DTO.convert(retrievedCar);
    }

    @RequestMapping(value = ResourcesConstants.PATH_CAR_WITH_CAR_ID, method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCar(@PathVariable(value = ResourcesConstants.CAR_ID_PATH_VARIABLE_NAME) String carId, @Valid @RequestBody CarDTO car) {
        UUID validatedCarId = ValidationUtil.validateIdFormat(carId);
        ValidationUtil.validatePresenceOfRegistrationNumberOnly(car);
        CarDTO registrationNumberCar = this.extractRegistrationNumberFromCar(car);
        registrationNumberCar.setCardId(validatedCarId);
        this.carService.updateCarDetails(validatedCarId, this.carDTO2BO.convert(registrationNumberCar));
    }

    @RequestMapping(value = ResourcesConstants.PATH_GARAGE_WITH_GARAGE_ID + ResourcesConstants.CAR_ALL_RESSOURCE, method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllCar(@PathVariable(value = ResourcesConstants.GARAGE_ID_PATH_VARIABLE_NAME) String garageId) {
        UUID validatedGarageId = ValidationUtil.validateIdFormat(garageId);
        this.carService.deleteAllCars(validatedGarageId);
    }

    @RequestMapping(value = ResourcesConstants.PATH_GARAGE_WITH_GARAGE_ID + ResourcesConstants.CAR_ALL_RESSOURCE, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CarsDTO getAllCar(@PathVariable(value = ResourcesConstants.GARAGE_ID_PATH_VARIABLE_NAME) String garageId,
                             @RequestParam(value = "color", required = false) String color,
                             @RequestParam(value = "minPrice", required = false) Float minPrice,
                             @RequestParam(value = "maxPrice", required = false) Float maxPrice) {

        UUID validatedGarageId = ValidationUtil.validateIdFormat(garageId);
        Set<CarBO> foundCars = this.carService.getAllCars(validatedGarageId, color, minPrice, maxPrice);
        CarsDTO returnedCars = new CarsDTO();
        List<CarDTO> carsList = new ArrayList<>();
        if (foundCars != null) {
            for (CarBO carBO :
                    foundCars) {
                carsList.add(this.carBO2DTO.convert(carBO));
            }
        }
        returnedCars.setCars(carsList);
        return returnedCars;
    }

    private CarDTO extractRegistrationNumberFromCar(CarDTO car) {
        CarDTO outputCar = new CarDTO();
        if (car != null) {
            outputCar.setRegistrationNumber(car.getRegistrationNumber());
        }
        return outputCar;
    }


}
