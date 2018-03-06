package com.stampyt.hello.respository.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Car {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @NotNull
    private String model;
    @NotNull
    private String brand;
    @NotNull
    private String color;
    @NotNull
    private String registrationNumber;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date commisioningDate;

    @NotNull
    private Float price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

}
