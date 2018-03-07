package com.stampyt.hello.controller.handler;

import com.stampyt.hello.service.exceptions.GarageNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestControllerAdvice
public class RestErrorHandler {

    private static final String INVALID_FORMAT = "INVALID_FORMAT";
    private static final String BAD_REQUEST = "BAD_REQUEST";
    private static final String GARAGE_NOT_FOUND = "GARAGE_NOT_FOUND";


    @ExceptionHandler(GarageNotFound.class)
    public ResponseEntity<ExceptionMessage> handleGarageNotFound(HttpServletRequest request, GarageNotFound exception) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(GARAGE_NOT_FOUND, exception.getMessage());
        return new ResponseEntity<>(exceptionMessage, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionMessage> handleInvalidArgument(HttpServletRequest request, MethodArgumentNotValidException exception) {
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionMessage> handleAnyErrors(HttpServletRequest request, Exception exception) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(BAD_REQUEST, exception.getMessage());
        return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
    }

}
