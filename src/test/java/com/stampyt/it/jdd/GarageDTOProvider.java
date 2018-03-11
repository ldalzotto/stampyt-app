package com.stampyt.it.jdd;


import com.stampyt.controller.constants.ResourcesConstants;
import com.stampyt.controller.model.CarDTO;
import com.stampyt.controller.model.GarageDTO;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class GarageDTOProvider {

    public static GarageDTO generateGarage(boolean withCars) {
        return GarageDTOProvider.generateGarage(withCars, null);
    }

    public static GarageDTO generateGarage(boolean withCars, Integer carNb, List<String> colorChoice, Float minPrice, Float maxPrice) {
        GarageDTO garageDTO = generateRandomGarage();
        if (withCars) {
            if (carNb == null) {
                carNb = RandomUtils.nextInt(1, 10);
            }
            if (colorChoice != null || maxPrice != null || minPrice != null) {
                garageDTO = addRandomCars(garageDTO, carNb, colorChoice, minPrice, maxPrice);
            } else {
                garageDTO = addRandomCars(garageDTO, carNb);
            }
        }
        return garageDTO;
    }

    public static GarageDTO generateGarage(boolean withCars, Integer carNb) {
        return generateGarage(withCars, carNb, null, null, null);
    }

    public static GarageDTO generateGarageDetails(String name, String address, Integer maxCapacity) {
        GarageDTO garageDTO = new GarageDTO();
        if (name != null) {
            garageDTO.setName(name);
        }
        if (address != null) {
            garageDTO.setAddress(address);
        }
        if (maxCapacity != null) {
            garageDTO.setMaxCapacity(maxCapacity);
        }
        return garageDTO;
    }

    private static GarageDTO generateRandomGarage() {
        GarageDTO garageDTO = new GarageDTO();
        garageDTO.setName(RandomStringUtils.random(10, true, false));
        garageDTO.setMaxCapacity(RandomUtils.nextInt(1, Integer.MAX_VALUE));
        garageDTO.setAddress(RandomStringUtils.random(10, true, false));
        return garageDTO;
    }


    private static GarageDTO addRandomCars(GarageDTO garageDTO, int carNb, List<String> colorChoice, Float minPrice, Float maxPrice) {
        List<CarDTO> generatedCars = new ArrayList<>();

        for (int i = 0; i < carNb; i++) {
            if (colorChoice != null || maxPrice != null || minPrice != null) {
                generatedCars.add(generateRandomCar(colorChoice, minPrice, maxPrice));
            } else {
                generatedCars.add(generateRandomCar());
            }
        }

        if (garageDTO != null) {
            garageDTO.setCars(generatedCars);
        }
        return garageDTO;
    }

    private static GarageDTO addRandomCars(GarageDTO garageDTO, int carNb) {
        return addRandomCars(garageDTO, carNb, null, null, null);
    }


    private static CarDTO generateRandomCar(List<String> colorChoice, Float minPrice, Float maxPrice) {
        CarDTO carDTO = new CarDTO();
        if (minPrice == null) {
            if (maxPrice == null) {
                carDTO.setPrice(RandomUtils.nextFloat(0.1F, Float.MAX_VALUE));
            } else {
                carDTO.setPrice(RandomUtils.nextFloat(0.1F, maxPrice));
            }
        } else {
            if (maxPrice == null) {
                carDTO.setPrice(RandomUtils.nextFloat(minPrice, Float.MAX_VALUE));
            } else {
                carDTO.setPrice(RandomUtils.nextFloat(minPrice, maxPrice));
            }
        }
        carDTO.setModel(RandomStringUtils.random(10));
        if (colorChoice != null) {
            Random rand = new Random();
            carDTO.setColor(colorChoice.get(rand.nextInt(colorChoice.size())));
        } else {
            carDTO.setColor(RandomStringUtils.random(10));
        }
        carDTO.setBrand(RandomStringUtils.random(10));
        carDTO.setCommisioningDate(DateTime.now(DateTimeZone.UTC));
        carDTO.setRegistrationNumber(RandomStringUtils.random(10));
        return carDTO;
    }

    public static CarDTO generateRandomCar() {
        return generateRandomCar(null, null, null);
    }

    public static ResponseEntity<GarageDTO> insertRandomGarage(boolean withCars, Integer carLimit, Integer nbOfCars, TestRestTemplate testRestTemplate) {
        GarageDTO garageDTO;
        if (nbOfCars == null) {
            if (carLimit == null) {
                garageDTO = GarageDTOProvider.generateGarage(withCars);
            } else {
                garageDTO = GarageDTOProvider.generateGarage(withCars, RandomUtils.nextInt(0, carLimit));
            }
        } else {
            if (carLimit == null) {
                garageDTO = GarageDTOProvider.generateGarage(withCars, nbOfCars);
            } else {
                garageDTO = GarageDTOProvider.generateGarage(withCars, nbOfCars);
                garageDTO.setMaxCapacity(carLimit);
            }
        }
        return testRestTemplate.postForEntity(URIRessourceProvider.buildGarageBasePath(), garageDTO, GarageDTO.class);
    }


    public static ResponseEntity<GarageDTO> insertGarage(GarageDTO garageDTO, TestRestTemplate testRestTemplate) {
        return testRestTemplate.postForEntity(URIRessourceProvider.buildGarageBasePath(), garageDTO, GarageDTO.class);
    }

    public static ResponseEntity<GarageDTO> insertRandomGarage(boolean withCars, TestRestTemplate testRestTemplate) {
        GarageDTO garageDTO = GarageDTOProvider.generateGarage(withCars);
        return testRestTemplate.postForEntity(URIRessourceProvider.buildGarageBasePath(), garageDTO, GarageDTO.class);
    }

    public static ResponseEntity<CarDTO> saveCar(CarDTO dto, UUID garageId, TestRestTemplate testRestTemplate) {
        return testRestTemplate.postForEntity(URIRessourceProvider.buildGarageBasePath(garageId.toString()) + ResourcesConstants.PATH_CAR,
                dto, CarDTO.class);
    }

}
