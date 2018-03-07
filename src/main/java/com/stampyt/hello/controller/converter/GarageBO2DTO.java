package com.stampyt.hello.controller.converter;

import com.stampyt.hello.controller.model.GarageDTO;
import com.stampyt.hello.service.model.GarageBO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class GarageBO2DTO implements Converter<GarageBO, GarageDTO> {

    public GarageBO2DTO(CarBO2DTO carBO2DTO) {
        this.carBO2DTO = carBO2DTO;
    }

    private CarBO2DTO carBO2DTO;

    @Override
    public GarageDTO convert(GarageBO source) {

        GarageDTO garageDTO = new GarageDTO();
        if (source != null) {
            garageDTO.setAddress(source.getAddress());
            garageDTO.setCreationDate(source.getCreationDate());
            garageDTO.setGarageId(source.getId());
            garageDTO.setMaxCapacity(source.getCarStorageLimit());
            garageDTO.setName(source.getName());

            if (source.getCars() != null) {
                garageDTO.setCars(source.getCars().stream().map(car -> this.carBO2DTO.convert(car)).collect(Collectors.toList()));
            }
        }
        return garageDTO;
    }
}
