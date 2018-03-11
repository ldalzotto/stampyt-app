package com.stampyt.hello.service.converter.car;

import com.stampyt.hello.respository.entity.Car;
import com.stampyt.hello.service.model.CarBO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CarBO2Car implements Converter<CarBO, Car> {

    @Override
    public Car convert(CarBO source) {
        Car car = new Car();
        if (source != null) {
            car.setId(source.getId());
            car.setBrand(source.getBrand());
            car.setColor(source.getColor());
            if (source.getCommisioningDate() != null) {
                car.setCommisioningDate(source.getCommisioningDate().toDate());
            }
            car.setModel(source.getModel());
            car.setRegistrationNumber(source.getRegistrationNumber());
            car.setPrice(source.getPrice());
        }
        return car;
    }
}
