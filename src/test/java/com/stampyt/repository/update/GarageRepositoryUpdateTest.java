package com.stampyt.repository.update;

import com.stampyt.repository.GarageUpdateRepository;
import com.stampyt.repository.entity.Garage;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import javax.persistence.EntityManager;

public class GarageRepositoryUpdateTest {

    private static GarageUpdateRepository garageUpdateRepository;

    @BeforeClass
    public static void init() {
        EntityManager mockedEntityManager = Mockito.mock(EntityManager.class);
        Mockito.when(mockedEntityManager.find(Mockito.any(), Mockito.any())).thenReturn(new Garage());
        garageUpdateRepository = new GarageUpdateRepositoryImpl(mockedEntityManager);
    }

    @Test
    public void updating_all_values_test() {
        Garage garageToSave = new Garage();
        garageToSave.setAddress("address");
        garageToSave.setCarStorageLimit(5);
        garageToSave.setName("name");

        Garage savedGarage = garageUpdateRepository.save(garageToSave);

        Assert.assertNotNull(savedGarage);
        Assert.assertNotNull(savedGarage.getAddress());
        Assert.assertNotNull(savedGarage.getCarStorageLimit());
        Assert.assertNotNull(savedGarage.getName());

    }


}
