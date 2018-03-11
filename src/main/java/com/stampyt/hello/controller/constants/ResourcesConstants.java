package com.stampyt.hello.controller.constants;

public class ResourcesConstants {

    public static final String GARAGE_ID_PATH_VARIABLE_NAME = "garage-id";
    public static final String GARAGE_PARAM_PATH_VARIABLE = "{" + GARAGE_ID_PATH_VARIABLE_NAME + "}";
    public static final String PATH_GARAGE_WITH_GARAGE_ID = "/garage/" + GARAGE_PARAM_PATH_VARIABLE;

    public static final String CAR_ID_PATH_VARIABLE_NAME = "car-id";
    public static final String CAR_PARAM_PATH_VARIABLE = "{" + CAR_ID_PATH_VARIABLE_NAME + "}";
    public static final String PATH_CAR = "/car";
    public static final String PATH_CAR_WITH_CAR_ID = PATH_CAR + "/" + CAR_PARAM_PATH_VARIABLE;
    public static final String CAR_NUMBER_RESSOURCE = "/car-number";
    public static final String CAR_ALL_RESSOURCE = "/car/all";
}
