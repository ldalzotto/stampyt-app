package com.stampyt.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.stampyt.controller.serializers.DateTimeWithZDeSerializer;
import com.stampyt.controller.serializers.DateTimeWithZSerializer;
import org.joda.time.DateTime;

import javax.validation.constraints.Min;
import java.util.Objects;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarDTO {

    private UUID carId;

    private String brand;

    private String model;

    private String color;

    private String registrationNumber;

    @JsonSerialize(using = DateTimeWithZSerializer.class)
    @JsonDeserialize(using = DateTimeWithZDeSerializer.class)
    private DateTime commisioningDate;

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

    public UUID getCarId() {
        return carId;
    }

    public void setCarId(UUID carId) {
        this.carId = carId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarDTO carDTO = (CarDTO) o;
        return Objects.equals(carId, carDTO.carId) &&
                Objects.equals(brand, carDTO.brand) &&
                Objects.equals(model, carDTO.model) &&
                Objects.equals(color, carDTO.color) &&
                Objects.equals(registrationNumber, carDTO.registrationNumber) &&
                Objects.equals(commisioningDate, carDTO.commisioningDate) &&
                Objects.equals(price, carDTO.price);
    }

    @Override
    public int hashCode() {

        return Objects.hash(carId, brand, model, color, registrationNumber, commisioningDate, price);
    }
}
