package com.stampyt.service.exceptions.createduplicate;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class CreateDuplicateHandler {

    public static <T, E extends RuntimeException> void handleDuplicatedEntityCreation(Exception e, Class<T> entityClassToCheck,
                                                                                      Class<E> errorToThrow) {
        String errorMessage = e.getMessage();
        if (errorMessage.contains("Multiple representations of the same entity")
                && errorMessage.contains(entityClassToCheck.getName())) {
            RuntimeException exception;
            try {
                Constructor exceptionConstructor = errorToThrow.getConstructor(Throwable.class);
                exception = (RuntimeException) exceptionConstructor.newInstance(e);
                throw exception;
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException
                    | InvocationTargetException e1) {
                throw new RuntimeException(e);
            }
        }

    }

}
