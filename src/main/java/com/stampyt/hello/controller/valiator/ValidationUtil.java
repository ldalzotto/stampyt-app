package com.stampyt.hello.controller.valiator;

import com.stampyt.hello.service.exceptions.InvalidIdFormat;

import java.util.UUID;

public class ValidationUtil {

    public static UUID validateIdFormat(String id) {
        try {
            return UUID.fromString(id);
        } catch (Exception e) {
            throw new InvalidIdFormat("The identifier " + id + " is malformed.", e);
        }
    }

}
