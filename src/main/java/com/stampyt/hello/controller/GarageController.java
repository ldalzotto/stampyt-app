package com.stampyt.hello.controller;

import com.stampyt.hello.controller.model.GarageDTO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class GarageController {

    private static final String GARAGE_ID_PATH_VARIABLE_NAME = "garage-id";

    @RequestMapping(value = "/garage", method = RequestMethod.POST)
    public GarageDTO createGarage(@Valid @RequestBody GarageDTO garage) {
        return garage;
    }

    @RequestMapping(value = "/garage/{" + GARAGE_ID_PATH_VARIABLE_NAME + "}", method = RequestMethod.PUT)
    public GarageDTO updateGarage(@PathVariable(value = GARAGE_ID_PATH_VARIABLE_NAME) String garageId, @Valid @RequestBody GarageDTO garage) {
        //TODO
        return garage;
    }

    @RequestMapping(value = "/garage/{" + GARAGE_ID_PATH_VARIABLE_NAME + "}", method = RequestMethod.DELETE)
    public void deleteGarage(@PathVariable(value = GARAGE_ID_PATH_VARIABLE_NAME) String garageId) {
        //TODO
    }

    @RequestMapping(value = "/garage/{" + GARAGE_ID_PATH_VARIABLE_NAME + "}", method = RequestMethod.GET)
    public GarageDTO getGarage(@PathVariable(value = GARAGE_ID_PATH_VARIABLE_NAME) String garageId) {
        //TODO
        return null;
    }

}
