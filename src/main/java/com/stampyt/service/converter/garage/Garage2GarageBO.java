package com.stampyt.service.converter.garage;


import com.stampyt.repository.entity.Garage;
import com.stampyt.service.converter.car.Car2CarBO;
import com.stampyt.service.model.GarageBO;
import org.joda.time.DateTime;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class Garage2GarageBO implements Converter<Garage, GarageBO> {

    public Garage2GarageBO(Car2CarBO car2CarBO) {
        this.car2CarBO = car2CarBO;
    }

    private Car2CarBO car2CarBO;

    @Override
    public GarageBO convert(Garage source) {
        GarageBO garageBO = new GarageBO();

        if (source != null) {
            garageBO.setId(source.getId());
            garageBO.setAddress(source.getAddress());
            garageBO.setCreationDate(new DateTime(source.getCreationDate()));
            garageBO.setCarStorageLimit(source.getCarStorageLimit());
            garageBO.setName(source.getName());

            if (source.getCars() != null) {
                garageBO.setCars(source.getCars().stream().map(car -> this.car2CarBO.convert(car)).collect(Collectors.toSet()));
            }
        }

        return garageBO;
    }
}
