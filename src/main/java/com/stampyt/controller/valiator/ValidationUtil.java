package com.stampyt.controller.valiator;

import com.stampyt.controller.model.CarDTO;
import com.stampyt.service.exceptions.InvalidArgumentException;
import com.stampyt.service.exceptions.InvalidIdFormat;

import java.lang.reflect.Field;
import java.util.UUID;

public class ValidationUtil {

    public static UUID validateIdFormat(String id) {
        try {
            return UUID.fromString(id);
        } catch (Exception e) {
            throw new InvalidIdFormat("The identifier " + id + " is malformed.", e);
        }
    }

    public static void validatePresenceOfRegistrationNumberOnly(CarDTO car) {
        Field[] fields = car.getClass().getDeclaredFields();
        try {
            for (Field field :
                    fields) {
                if (!field.isSynthetic()) {
                    if (!field.getName().equals("registrationNumber")) {
                        field.setAccessible(true);
                        if (field.get(car) != null) {
                            field.setAccessible(false);
                            throw new InvalidArgumentException("The field : " + field.getName() + " cannot be updated.");
                        }
                        field.setAccessible(false);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
