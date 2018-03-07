package com.stampyt.hello.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.stampyt.hello.controller.serializers.DateTimeWithZDeSerializer;
import com.stampyt.hello.controller.serializers.DateTimeWithZSerializer;
import org.joda.time.DateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarDTO {

    @NotNull(message = "Car brand should be provided.")
    private String brand;

    @NotNull(message = "Car model should be provided.")
    private String model;

    @NotNull(message = "Car color should be provided.")
    private String color;

    @NotNull(message = "Registration number should be provided.")
    private String registrationNumber;

    @JsonSerialize(using = DateTimeWithZSerializer.class)
    @JsonDeserialize(using = DateTimeWithZDeSerializer.class)
    @NotNull(message = "Commisioning date should be provided.")
    private DateTime commisioningDate;

    @NotNull(message = "Car price should be provided.")
    @Min(value = 0, message = "Car price should be > 0.")
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

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
}
