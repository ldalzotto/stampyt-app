package com.stampyt.repository.update;

import com.stampyt.repository.CarUpdateRepository;
import com.stampyt.repository.entity.Car;
import com.stampyt.repository.entity.Garage;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import javax.persistence.EntityManager;

public class CarRepositoryUpdateTest {

    private static CarUpdateRepository carUpdateRepository;

    @BeforeClass
    public static void init() {
        EntityManager mockedEntityManager = Mockito.mock(EntityManager.class);
        Mockito.when(mockedEntityManager.find(Mockito.any(), Mockito.any())).thenReturn(new Car());
        carUpdateRepository = new CarUpdateRepositoryImpl(mockedEntityManager);
    }

    @Test
    public void updating_all_values_test() {
        Car carTosave = new Car();
        carTosave.setBrand("brand");
        carTosave.setColor("color");
        carTosave.setCommisioningDate(DateTime.now().toDate());
        carTosave.setGarage(new Garage());
        carTosave.setModel("model");
        carTosave.setPrice(9f);
        carTosave.setRegistrationNumber("registration");

        Car savedCar = carUpdateRepository.save(carTosave);

        Assert.assertNotNull(savedCar);
        Assert.assertNotNull(savedCar.getBrand());
        Assert.assertNotNull(savedCar.getColor());
        Assert.assertNotNull(savedCar.getCommisioningDate());
        Assert.assertNotNull(savedCar.getRegistrationNumber());

    }

}
