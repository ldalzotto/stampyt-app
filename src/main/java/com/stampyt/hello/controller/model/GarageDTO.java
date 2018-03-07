package com.stampyt.hello.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.stampyt.hello.controller.serializers.DateTimeWithZDeSerializer;
import com.stampyt.hello.controller.serializers.DateTimeWithZSerializer;
import org.joda.time.DateTime;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GarageDTO {

    private UUID garageId;

    @NotNull(message = "Garage name should be provided.")
    private String name;
    @NotNull(message = "Garage address should be provided.")
    private String address;

    @NotNull(message = "Garage max capacity should be provided.")
    @Min(value = 1, message = "Garage max capacity should be > 0.")
    private Integer maxCapacity;

    @JsonSerialize(using = DateTimeWithZSerializer.class)
    @JsonDeserialize(using = DateTimeWithZDeSerializer.class)
    private DateTime creationDate;

    @Valid
    private List<CarDTO> cars;

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

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public List<CarDTO> getCars() {
        return cars;
    }

    public void setCars(List<CarDTO> cars) {
        this.cars = cars;
    }

    public UUID getGarageId() {
        return garageId;
    }

    public void setGarageId(UUID garageId) {
        this.garageId = garageId;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }
}
