package com.stampyt.hello.respository.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Garage {

    @Id
    private String id;

    @NotNull
    private String name;

    @NotNull
    private String address;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @NotNull
    private Integer carStorageLimit;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "garage")
    private Set<Car> cars = new HashSet<>();

    public String getId() {
        return id;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getCarStorageLimit() {
        return carStorageLimit;
    }

    public void setCarStorageLimit(Integer carStorageLimit) {
        this.carStorageLimit = carStorageLimit;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }

    public void setId(String id) {
        this.id = id;
    }
}
