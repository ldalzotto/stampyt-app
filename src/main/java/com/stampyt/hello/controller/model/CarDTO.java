package com.stampyt.hello.controller.model;

import org.joda.time.DateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class CarDTO {

    private UUID carid;

    @NotNull
    private String brand;

    @NotNull
    private String model;
    @NotNull
    private String color;

    private DateTime commisioningDate;

    @NotNull
    @Min(value = 0)
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
}
