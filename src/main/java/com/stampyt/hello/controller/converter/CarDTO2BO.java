package com.stampyt.hello.controller.converter;

import com.stampyt.hello.controller.model.CarDTO;
import com.stampyt.hello.service.model.CarBO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CarDTO2BO implements Converter<CarDTO, CarBO> {

    @Override
    public CarBO convert(CarDTO source) {
        CarBO carBO = new CarBO();
        if (source != null) {
            carBO.setBrand(source.getBrand());
            carBO.setColor(source.getColor());
            carBO.setCommisioningDate(source.getCommisioningDate());
            carBO.setRegistrationNumber(source.getRegistrationNumber());
            carBO.setModel(source.getModel());
            carBO.setPrice(source.getPrice());
        }
        return carBO;
    }
}
