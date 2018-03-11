package com.stampyt.it.jdd;

import com.stampyt.hello.controller.constants.ResourcesConstants;

public class URIRessourceProvider {

    public static String buildGarageBasePath() {
        return buildGarageBasePath(null);
    }

    public static String buildGarageBasePath(String garageId) {
        if (garageId == null) {
            return ResourcesConstants.PATH_GARAGE_WITH_GARAGE_ID.replace("/" + ResourcesConstants.GARAGE_PARAM_PATH_VARIABLE, "");
        } else {
            return ResourcesConstants.PATH_GARAGE_WITH_GARAGE_ID.replace(ResourcesConstants.GARAGE_PARAM_PATH_VARIABLE, garageId);
        }
    }

    public static String buildCarBasePath(String carId) {
        if (carId == null) {
            return ResourcesConstants.PATH_CAR_WITH_CAR_ID.replace("/" + ResourcesConstants.CAR_PARAM_PATH_VARIABLE, "");
        } else {
            return ResourcesConstants.PATH_CAR_WITH_CAR_ID.replace(ResourcesConstants.CAR_PARAM_PATH_VARIABLE, carId);
        }
    }


}
