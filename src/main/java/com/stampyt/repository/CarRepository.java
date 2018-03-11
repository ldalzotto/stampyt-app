package com.stampyt.repository;


import com.stampyt.repository.entity.Car;
import com.stampyt.repository.entity.Garage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface CarRepository extends CrudRepository<Car, UUID> {

    Integer countAllByGarage(Garage garageId);

    void deleteCarsByGarage(Garage garageId);

    @Query("select c from Car c where c.garage = ?1 and (?2 = '' OR ?2 is null OR LOWER(c.color) = LOWER(?2) ) and (?3 is null OR c.price >= ?3) " +
            "and (?4 is null OR c.price <= ?4 )")
    List<Car> findCarsByGarageAndColorAndPriceGreaterThanEqualAndPriceLessThanEqual(Garage garageId, String color, Float minPrice,
                                                                                    Float maxPrice);


}
