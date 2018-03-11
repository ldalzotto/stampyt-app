package com.stampyt.controller.model;

import java.util.UUID;

public class GarageCarNumberDTO {

    private UUID garageId;
    private Integer carNumber;

    public UUID getGarageId() {
        return garageId;
    }

    public void setGarageId(UUID garageId) {
        this.garageId = garageId;
    }

    public Integer getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(Integer carNumber) {
        this.carNumber = carNumber;
    }
}
