package com.stampyt.hello.controller;

import com.stampyt.hello.controller.converter.GarageBO2DTO;
import com.stampyt.hello.controller.converter.GarageDTO2BO;
import com.stampyt.hello.controller.model.GarageDTO;
import com.stampyt.hello.controller.valiator.ValidationUtil;
import com.stampyt.hello.service.GarageService;
import com.stampyt.hello.service.model.GarageBO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static com.stampyt.hello.controller.constants.ResourcesConstants.GARAGE_ID_PATH_VARIABLE_NAME;
import static com.stampyt.hello.controller.constants.ResourcesConstants.PATH_GARAGE;
import static com.stampyt.hello.controller.constants.ResourcesConstants.PATH_GARAGE_WITH_GARAGE_ID;

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

    @RequestMapping(value = PATH_GARAGE, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public GarageDTO createGarage(@Valid @RequestBody GarageDTO garage) {
        GarageBO garageBO = this.garageDTO2BO.convert(garage);
        GarageBO createdGarage = this.garageService.createGarage(garageBO);
        return this.garageBO2DTO.convert(createdGarage);
    }

    @RequestMapping(value = PATH_GARAGE_WITH_GARAGE_ID, method = RequestMethod.PUT)
    public GarageDTO updateGarage(@PathVariable(value = GARAGE_ID_PATH_VARIABLE_NAME) String garageId, @Valid @RequestBody GarageDTO garage) {
        UUID garageIdValidated = ValidationUtil.validateIdFormat(garageId);
        this.noCarsAllowed(garage);
        garage.setGarageId(garageIdValidated);
        GarageBO garageValuesToUpdate = this.garageDTO2BO.convert(garage);
        GarageBO updatedGarage = this.garageService.updateGarage(garageIdValidated, garageValuesToUpdate);
        return this.garageBO2DTO.convert(updatedGarage);
    }

    @RequestMapping(value = PATH_GARAGE_WITH_GARAGE_ID, method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGarage(@PathVariable(value = GARAGE_ID_PATH_VARIABLE_NAME) String garageId) {
        UUID garageIdValidated = ValidationUtil.validateIdFormat(garageId);
        this.garageService.deleteGarage(garageIdValidated);
    }

    @RequestMapping(value = PATH_GARAGE_WITH_GARAGE_ID, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public GarageDTO getGarage(@PathVariable(value = GARAGE_ID_PATH_VARIABLE_NAME) String garageId) {
        UUID garageIdValidated = ValidationUtil.validateIdFormat(garageId);
        GarageBO foundedGarage = this.garageService.getGarage(garageIdValidated);
        return this.garageBO2DTO.convert(foundedGarage);
    }


    private void noCarsAllowed(GarageDTO garageDTO) {
        if (garageDTO != null && garageDTO.getCars() != null) {
            if (garageDTO.getCars().size() > 0) {

            }
        }
    }

}
