package com.stampyt.service.model;

import org.joda.time.DateTime;

import java.util.UUID;

public class CarBO {

    private UUID id;
    private String brand;
    private String model;
    private String color;
    private String registrationNumber;
    private DateTime commisioningDate;
    private Float price;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public DateTime getCommisioningDate() {
        return commisioningDate;
    }

    public void setCommisioningDate(DateTime commisioningDate) {
        this.commisioningDate = commisioningDate;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
