package com.stampyt.it.jdd;


import com.stampyt.controller.model.CarsDTO;
import org.junit.Assert;

public class GetCarsFilterAsserter {

    public static void assertCars(CarsDTO responseCars, CarsDTO inputCars, String color, Float minPrice, Float maxprice) {
        long awaitedCarNumber = inputCars.getCars().stream().filter(c -> {
            if (color == null) {
                return true;
            } else if (c.getColor().toLowerCase().equals(color)) {
                return true;
            }
            return false;
        })
                .filter(c -> {
                    if (minPrice == null) {
                        return true;
                    } else if (c.getPrice() >= minPrice) {
                        return true;
                    }
                    return false;
                })
                .filter(c -> {
                    if (maxprice == null) {
                        return true;
                    } else if (c.getPrice() <= maxprice) {
                        return true;
                    }
                    return false;
                })
                .count();

        Assert.assertEquals(awaitedCarNumber, responseCars.getCars().size());
    }


}
