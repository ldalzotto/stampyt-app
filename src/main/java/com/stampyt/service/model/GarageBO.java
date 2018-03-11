package com.stampyt.service.model;

import org.joda.time.DateTime;

import java.util.Set;
import java.util.UUID;

public class GarageBO {

    private UUID id;
    private String name;
    private String address;
    private DateTime creationDate;
    private Integer carStorageLimit;
    private Set<CarBO> cars;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getCarStorageLimit() {
        return carStorageLimit;
    }

    public void setCarStorageLimit(Integer carStorageLimit) {
        this.carStorageLimit = carStorageLimit;
    }

    public Set<CarBO> getCars() {
        return cars;
    }

    public void setCars(Set<CarBO> cars) {
        this.cars = cars;
    }
}
