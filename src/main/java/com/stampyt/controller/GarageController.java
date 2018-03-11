package com.stampyt.controller;

import com.stampyt.controller.constants.ResourcesConstants;
import com.stampyt.controller.converter.GarageBO2DTO;
import com.stampyt.controller.converter.GarageDTO2BO;
import com.stampyt.controller.model.GarageDTO;
import com.stampyt.controller.valiator.ValidationUtil;
import com.stampyt.service.GarageService;
import com.stampyt.service.exceptions.InvalidArgumentException;
import com.stampyt.service.model.GarageBO;
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

    @RequestMapping(value = ResourcesConstants.PATH_GARAGE, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public GarageDTO createGarage(@Valid @RequestBody GarageDTO garage) {
        this.garageCreationCarNumberConstraint(garage);

        GarageBO garageBO = this.garageDTO2BO.convert(garage);
        GarageBO createdGarage = this.garageService.createGarage(garageBO);
        return this.garageBO2DTO.convert(createdGarage);
    }

    @RequestMapping(value = ResourcesConstants.PATH_GARAGE_WITH_GARAGE_ID, method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateGarage(@PathVariable(value = ResourcesConstants.GARAGE_ID_PATH_VARIABLE_NAME) String garageId, @Valid @RequestBody GarageDTO garage) {
        UUID garageIdValidated = ValidationUtil.validateIdFormat(garageId);
        this.noCarsAllowed(garage);
        this.noModificationOfSensisitiveGarageInfo(garage);

        garage.setGarageId(garageIdValidated);
        GarageBO garageValuesToUpdate = this.garageDTO2BO.convert(garage);
        this.garageService.updateGarage(garageIdValidated, garageValuesToUpdate);
    }

    @RequestMapping(value = ResourcesConstants.PATH_GARAGE_WITH_GARAGE_ID, method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGarage(@PathVariable(value = ResourcesConstants.GARAGE_ID_PATH_VARIABLE_NAME) String garageId) {
        UUID garageIdValidated = ValidationUtil.validateIdFormat(garageId);
        this.garageService.deleteGarage(garageIdValidated);
    }

    @RequestMapping(value = ResourcesConstants.PATH_GARAGE_WITH_GARAGE_ID, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public GarageDTO getGarage(@PathVariable(value = ResourcesConstants.GARAGE_ID_PATH_VARIABLE_NAME) String garageId) {
        UUID garageIdValidated = ValidationUtil.validateIdFormat(garageId);
        GarageBO foundedGarage = this.garageService.getGarage(garageIdValidated);
        return this.garageBO2DTO.convert(foundedGarage);
    }


    private void garageCreationCarNumberConstraint(GarageDTO garageDTO) {
        if (garageDTO != null && garageDTO.getCars() != null && !garageDTO.getCars().isEmpty()) {
            if (garageDTO.getMaxCapacity() == null) {
                throw new InvalidArgumentException("Cannot create garage with cars while garage max capacity is not provided.");
            }
            if (garageDTO.getMaxCapacity() < garageDTO.getCars().size()) {
                throw new InvalidArgumentException("Trying to create garage with number of cars : " + garageDTO.getCars().size() + "" +
                        " higher than max capacity : " + garageDTO.getMaxCapacity());
            }
        }
    }

    private void noCarsAllowed(GarageDTO garageDTO) {
        if (garageDTO != null && garageDTO.getCars() != null) {
            if (garageDTO.getCars().size() > 0) {
                throw new InvalidArgumentException("Impossible to modify cars.");
            }
        }
    }

    private void noModificationOfSensisitiveGarageInfo(GarageDTO garageDTO) {
        if (garageDTO != null) {
            if (garageDTO.getGarageId() != null) {
                throw new InvalidArgumentException("Impossible to change garageId");
            }
            if (garageDTO.getCreationDate() != null) {
                throw new InvalidArgumentException("Impossible to change garage creation date.");
            }
        }
    }

}
