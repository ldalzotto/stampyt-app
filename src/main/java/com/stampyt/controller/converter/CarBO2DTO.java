package com.stampyt.controller.converter;

import com.stampyt.controller.model.CarDTO;
import com.stampyt.service.model.CarBO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CarBO2DTO implements Converter<CarBO, CarDTO> {
    @Override
    public CarDTO convert(CarBO source) {
        CarDTO carDTO = new CarDTO();
        if (source != null) {
            carDTO.setBrand(source.getBrand());
            carDTO.setColor(source.getColor());
            carDTO.setCommisioningDate(source.getCommisioningDate());
            carDTO.setModel(source.getModel());
            carDTO.setRegistrationNumber(source.getRegistrationNumber());
            carDTO.setPrice(source.getPrice());
            carDTO.setCarId(source.getId());
        }
        return carDTO;
    }
}
