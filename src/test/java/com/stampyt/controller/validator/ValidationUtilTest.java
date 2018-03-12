package com.stampyt.controller.validator;

import com.stampyt.controller.model.CarDTO;
import com.stampyt.controller.valiator.ValidationUtil;
import com.stampyt.service.exceptions.InvalidArgumentException;
import org.junit.Test;

public class ValidationUtilTest {

    @Test
    public void resgirationNumber_null_test() {
        ValidationUtil.validatePresenceOfRegistrationNumberOnly(null);
    }

    @Test
    public void resgirationNumber_empty_test() {
        ValidationUtil.validatePresenceOfRegistrationNumberOnly(new CarDTO());
    }

    @Test(expected = InvalidArgumentException.class)
    public void resgirationNumber_manyfields_test() {
        CarDTO carDTO = new CarDTO();
        carDTO.setBrand("brand");
        carDTO.setRegistrationNumber("reg");
        ValidationUtil.validatePresenceOfRegistrationNumberOnly(carDTO);
    }

    @Test(expected = InvalidArgumentException.class)
    public void resgirationNumber_manyfields_without_registration_test() {
        CarDTO carDTO = new CarDTO();
        carDTO.setBrand("brand");
        ValidationUtil.validatePresenceOfRegistrationNumberOnly(carDTO);
    }

    @Test
    public void resgirationNumber_nominal_test() {
        CarDTO carDTO = new CarDTO();
        carDTO.setRegistrationNumber("reg");
        ValidationUtil.validatePresenceOfRegistrationNumberOnly(carDTO);
    }
}
