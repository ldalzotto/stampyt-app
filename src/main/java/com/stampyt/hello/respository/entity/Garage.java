package com.stampyt.hello.respository.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Garage {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "car")
    private Set<Car> cars = new HashSet<>();

}
