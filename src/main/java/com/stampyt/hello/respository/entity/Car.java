package com.stampyt.hello.respository.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Car {

    @Id
    @Column(name = "registrationNumber")
    private String registrationNumber;

    @NotNull
    private String model;
    @NotNull
    private String brand;
    @NotNull
    private String color;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date commisioningDate;

    @NotNull
    private Float price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "garage_id", nullable = false)
    private Garage garage;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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

    public Date getCommisioningDate() {
        return commisioningDate;
    }

    public void setCommisioningDate(Date commisioningDate) {
        this.commisioningDate = commisioningDate;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Garage getGarage() {
        return garage;
    }

    public void setGarage(Garage garage) {
        this.garage = garage;
    }
}
