package com.stampyt.hello.service.converter.car;

import com.stampyt.hello.respository.entity.Car;
import com.stampyt.hello.service.model.CarBO;
import org.joda.time.DateTime;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class Car2CarBO implements Converter<Car, CarBO> {

    @Override
    public CarBO convert(Car source) {
        CarBO carBO = new CarBO();

        if (source != null) {
            carBO.setBrand(source.getBrand());
            carBO.setColor(source.getColor());
            carBO.setCommisioningDate(new DateTime(source.getCommisioningDate()));
            carBO.setModel(source.getModel());
            carBO.setRegistrationNumber(source.getRegistrationNumber());
            carBO.setPrice(source.getPrice());
            carBO.setId(source.getId());
        }

        return carBO;
    }
}
