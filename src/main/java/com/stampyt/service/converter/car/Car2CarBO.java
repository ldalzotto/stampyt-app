package com.stampyt.service.converter.car;


import com.stampyt.repository.entity.Car;
import com.stampyt.service.model.CarBO;
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
            if (source.getCommisioningDate() != null) {
                carBO.setCommisioningDate(new DateTime(source.getCommisioningDate()));
            }
            carBO.setModel(source.getModel());
            carBO.setRegistrationNumber(source.getRegistrationNumber());
            carBO.setPrice(source.getPrice());
            carBO.setId(source.getId());
        }

        return carBO;
    }
}
