package com.stampyt.controller.converter;


import com.stampyt.controller.model.GarageDTO;
import com.stampyt.service.model.GarageBO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class GarageDTO2BO implements Converter<GarageDTO, GarageBO> {

    public GarageDTO2BO(CarDTO2BO carDTO2BO) {
        this.carDTO2BO = carDTO2BO;
    }

    private CarDTO2BO carDTO2BO;

    @Override
    public GarageBO convert(GarageDTO source) {
        GarageBO garageBO = new GarageBO();
        if (source != null) {
            garageBO.setAddress(source.getAddress());
            garageBO.setCreationDate(source.getCreationDate());
            garageBO.setId(source.getGarageId());
            garageBO.setCarStorageLimit(source.getMaxCapacity());
            garageBO.setName(source.getName());

            if (source.getCars() != null) {
                garageBO.setCars(source.getCars().stream().map(car -> this.carDTO2BO.convert(car)).collect(Collectors.toSet()));
            }
        }
        return garageBO;
    }
}
