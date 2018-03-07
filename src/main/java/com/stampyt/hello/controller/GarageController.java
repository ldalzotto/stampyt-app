package com.stampyt.hello.controller;

import com.stampyt.hello.controller.converter.GarageBO2DTO;
import com.stampyt.hello.controller.converter.GarageDTO2BO;
import com.stampyt.hello.controller.model.GarageDTO;
import com.stampyt.hello.service.GarageService;
import com.stampyt.hello.service.exceptions.InvalidIdFormat;
import com.stampyt.hello.service.model.GarageBO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
public class GarageController {

    public GarageController(GarageService garageService,
                            GarageDTO2BO garageDTO2BO,
                            GarageBO2DTO garageBO2DTO) {
        this.garageService = garageService;
        this.garageDTO2BO = garageDTO2BO;
        this.garageBO2DTO = garageBO2DTO;
    }

    private GarageDTO2BO garageDTO2BO;
    private GarageBO2DTO garageBO2DTO;
    private GarageService garageService;

    private static final String GARAGE_ID_PATH_VARIABLE_NAME = "garage-id";
    private static final String PATH_GARAGE_WITH_GARAGE_ID = "/garage/{" + GARAGE_ID_PATH_VARIABLE_NAME + "}";

    @RequestMapping(value = "/garage", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public GarageDTO createGarage(@Valid @RequestBody GarageDTO garage) {
        GarageBO garageBO = this.garageDTO2BO.convert(garage);
        GarageBO createdGarage = this.garageService.createGarage(garageBO);
        return this.garageBO2DTO.convert(createdGarage);
    }

    @RequestMapping(value = PATH_GARAGE_WITH_GARAGE_ID, method = RequestMethod.PUT)
    public GarageDTO updateGarage(@PathVariable(value = GARAGE_ID_PATH_VARIABLE_NAME) String garageId, @Valid @RequestBody GarageDTO garage) {
        //TODO
        return garage;
    }

    @RequestMapping(value = PATH_GARAGE_WITH_GARAGE_ID, method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGarage(@PathVariable(value = GARAGE_ID_PATH_VARIABLE_NAME) String garageId) {
        UUID garageIdValidated = this.validateIdFormat(garageId);
        this.garageService.deleteGarage(garageIdValidated);
    }

    @RequestMapping(value = PATH_GARAGE_WITH_GARAGE_ID, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public GarageDTO getGarage(@PathVariable(value = GARAGE_ID_PATH_VARIABLE_NAME) String garageId) {
        UUID garageIdValidated = this.validateIdFormat(garageId);
        GarageBO foundedGarage = this.garageService.getGarage(garageIdValidated);
        return this.garageBO2DTO.convert(foundedGarage);
    }

    private UUID validateIdFormat(String id) {
        try {
            return UUID.fromString(id);
        } catch (Exception e) {
            throw new InvalidIdFormat("The identifier " + id + " is malformed.", e);
        }
    }

}
