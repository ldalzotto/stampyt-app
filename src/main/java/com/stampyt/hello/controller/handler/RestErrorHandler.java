package com.stampyt.hello.controller.handler;

import com.stampyt.hello.service.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestControllerAdvice
public class RestErrorHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestErrorHandler.class);

    public static final String INVALID_FORMAT = "INVALID_FORMAT";
    public static final String BAD_REQUEST = "BAD_REQUEST";
    public static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";
    public static final String GARAGE_NOT_FOUND = "GARAGE_NOT_FOUND";
    public static final String CAR_NOT_FOUND = "CAR_NOT_FOUND";
    public static final String GARAGE_MAX_CAPACITY_UNDEFINED = "GARAGE_MAX_CAPACITY_UNDEFINED";

    @ExceptionHandler(GarageNotFound.class)
    public ResponseEntity<ExceptionMessage> handleGarageNotFound(HttpServletRequest request, GarageNotFound exception) {
        LOGGER.error(exception.getMessage(), exception);

        ExceptionMessage exceptionMessage = new ExceptionMessage(GARAGE_NOT_FOUND, exception.getMessage());
        return new ResponseEntity<>(exceptionMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CarNotFound.class)
    public ResponseEntity<ExceptionMessage> handleCarNotFound(HttpServletRequest request, CarNotFound exception) {
        LOGGER.error(exception.getMessage(), exception);

        ExceptionMessage exceptionMessage = new ExceptionMessage(CAR_NOT_FOUND, exception.getMessage());
        return new ResponseEntity<>(exceptionMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionMessage> handleMethodArgumentTypeMismatchException(HttpServletRequest request, MethodArgumentTypeMismatchException exception) {
        LOGGER.error(exception.getMessage(), exception);

        String messageError = "Field " + exception.getName() + " is malformed. Required type is : " + exception.getRequiredType().getName();

        ExceptionMessage exceptionMessage = new ExceptionMessage(INVALID_FORMAT, messageError);
        return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GarageMaxCapacityNotDefined.class)
    public ResponseEntity<ExceptionMessage> hangleGarageMaxCapacityNotDefined(HttpServletRequest request, GarageMaxCapacityNotDefined exception) {
        LOGGER.error(exception.getMessage(), exception);

        ExceptionMessage exceptionMessage = new ExceptionMessage(GARAGE_MAX_CAPACITY_UNDEFINED, exception.getMessage());
        return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MaxGarageCapacityReached.class)
    public ResponseEntity<ExceptionMessage> hangleMaxGarageCapacityReached(HttpServletRequest request, MaxGarageCapacityReached exception) {
        LOGGER.error(exception.getMessage(), exception);

        ExceptionMessage exceptionMessage = new ExceptionMessage(BAD_REQUEST, exception.getMessage());
        return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoCarFoundForGarage.class)
    public ResponseEntity<ExceptionMessage> hangleNoCarFoundForGarage(HttpServletRequest request, NoCarFoundForGarage exception) {
        LOGGER.error(exception.getMessage(), exception);

        ExceptionMessage exceptionMessage = new ExceptionMessage(CAR_NOT_FOUND, exception.getMessage());
        return new ResponseEntity<>(exceptionMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<ExceptionMessage> hangleInvalidArgumentException(HttpServletRequest request, InvalidArgumentException exception) {
        LOGGER.error(exception.getMessage(), exception);

        ExceptionMessage exceptionMessage = new ExceptionMessage(BAD_REQUEST, exception.getMessage());
        return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidIdFormat.class)
    public ResponseEntity<ExceptionMessage> hangleInvalidIdFormat(HttpServletRequest request, InvalidIdFormat exception) {
        LOGGER.error(exception.getMessage(), exception);

        ExceptionMessage exceptionMessage = new ExceptionMessage(BAD_REQUEST, exception.getMessage());
        return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionMessage> handleInvalidArgument(HttpServletRequest request, MethodArgumentNotValidException exception) {
        LOGGER.error(exception.getMessage(), exception);

        List<ObjectError> objectErrors = exception.getBindingResult().getAllErrors();
        StringBuilder errorMessage = new StringBuilder();
        for (ObjectError objectError :
                objectErrors) {
            errorMessage.append("On field ").append(objectError.getObjectName());
            if (objectError instanceof FieldError) {
                errorMessage.append("." + ((FieldError) objectError).getField());
            }
            errorMessage.append(" : ");
            errorMessage.append(objectError.getDefaultMessage()).append(" ");
        }
        ExceptionMessage exceptionMessage = new ExceptionMessage(INVALID_FORMAT, errorMessage.toString());
        return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionMessage> handleHttpMessageNotReadableException(HttpServletRequest request, HttpMessageNotReadableException exception) {
        LOGGER.error(exception.getMessage(), exception);

        ExceptionMessage exceptionMessage = new ExceptionMessage(INTERNAL_SERVER_ERROR, exception.getMessage().split(";")[0]);
        return new ResponseEntity<>(exceptionMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionMessage> handleAnyErrors(HttpServletRequest request, Exception exception) {
        LOGGER.error(exception.getMessage(), exception);
        ExceptionMessage exceptionMessage = new ExceptionMessage(INTERNAL_SERVER_ERROR, exception.getMessage());
        return new ResponseEntity<>(exceptionMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
