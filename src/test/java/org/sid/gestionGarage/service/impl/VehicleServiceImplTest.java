package org.sid.gestionGarage.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.sid.gestionGarage.exception.NotFoundException;
import org.sid.gestionGarage.exception.QuotaExceededException;
import org.sid.gestionGarage.model.Garage;
import org.sid.gestionGarage.model.Vehicle;
import org.sid.gestionGarage.repository.GarageRepository;
import org.sid.gestionGarage.repository.VehicleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class VehicleServiceImplTest {

    @Mock
    private VehicleRepository vRepo;
    @Mock
    private GarageRepository gRepo;

    @InjectMocks
    private VehicleServiceImpl service;

    @Mock
    private org.springframework.kafka.core.KafkaTemplate<String, Vehicle> kafkaTemplate;

    private UUID garageId;
    private Garage garage;
    private Vehicle vehicle;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        garageId = UUID.randomUUID();
        garage = Garage.builder().id(garageId).vehicles(new ArrayList<>()).build();
        vehicle = Vehicle.builder().id(UUID.randomUUID()).brand("Clio").manufactureYear(2020).fuelType("Essence").build();
    }

    @Test
    void create_ShouldSave_WhenUnderQuota() throws Exception {
        when(gRepo.findById(garageId)).thenReturn(Optional.of(garage));
        // empty garage, under quota
        Vehicle toSave = vehicle;
        Vehicle saved = Vehicle.builder().id(vehicle.getId()).brand(vehicle.getBrand()).garage(garage).build();
        when(vRepo.save(any(Vehicle.class))).thenReturn(saved);
        Vehicle result = service.create(garageId, toSave);
        assertEquals(garage, result.getGarage());
        verify(vRepo).save(toSave);
    }

    @Test void create_quotaExceeded() {
        garage.setVehicles(Collections.nCopies(50, new Vehicle()));
        when(gRepo.findById(garageId)).thenReturn(Optional.of(garage));
        assertThrows(QuotaExceededException.class, () -> service.create(garageId, vehicle));
    }


    @Test
    void update_ShouldThrowNotFound_WhenGarageMissing() {
        when(gRepo.findById(garageId)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> service.update(garageId, vehicle.getId(), vehicle));
    }

    @Test
    void delete_ShouldThrowNotFound_WhenVehicleMissing() {
        when(vRepo.findById(vehicle.getId())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> service.delete(garageId, vehicle.getId()));
    }

    @Test
    void listByGarage_ShouldReturnPage_WhenExists() {
        PageRequest pr = PageRequest.of(0, 5);
        when(gRepo.existsById(garageId)).thenReturn(true);
        Page<Vehicle> page = new PageImpl<>(List.of(vehicle));
        when(vRepo.findByGarageId(garageId, pr)).thenReturn(page);
        Page<Vehicle> result = service.listByGarage(garageId, pr);
        assertEquals(1, result.getTotalElements());
    }
}