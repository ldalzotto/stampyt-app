package com.stampyt.hello.service.converter.garage;

import com.stampyt.hello.respository.entity.Garage;
import com.stampyt.hello.service.converter.car.CarBO2Car;
import com.stampyt.hello.service.model.GarageBO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class GarageBO2Garage implements Converter<GarageBO, Garage> {

    public GarageBO2Garage(CarBO2Car carBO2Car) {
        this.carBO2Car = carBO2Car;
    }

    private CarBO2Car carBO2Car;

    @Override
    public Garage convert(GarageBO source) {

        Garage garage = new Garage();
        if (source != null) {
            garage.setAddress(source.getAddress());
            garage.setId(source.getId().toString());
            garage.setCreationDate(source.getCreationDate().toDate());
            garage.setCarStorageLimit(source.getCarStorageLimit());
            garage.setName(source.getName());

            if (source.getCars() != null) {
                garage.setCars(source.getCars().stream().map(car -> this.carBO2Car.convert(car))
                        .peek(car -> car.setGarage(garage)).collect(Collectors.toSet()));
            }
        }

        return garage;
    }
}
