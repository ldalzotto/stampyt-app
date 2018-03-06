package com.stampyt.hello.service.converter.car;

import com.stampyt.hello.respository.entity.Car;
import com.stampyt.hello.service.model.CarBO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CarBO2Car implements Converter<Car, CarBO> {

    @Override
    public CarBO convert(Car source) {
        //TODO
        return null;
    }
}
