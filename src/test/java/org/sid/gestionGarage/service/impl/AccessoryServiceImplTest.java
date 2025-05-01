package org.sid.gestionGarage.service.impl;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.sid.gestionGarage.exception.NotFoundException;
import org.sid.gestionGarage.model.Accessory;
import org.sid.gestionGarage.model.Vehicle;
import org.sid.gestionGarage.repository.AccessoryRepository;
import org.sid.gestionGarage.repository.VehicleRepository;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccessoryServiceImplTest {

    @Mock private AccessoryRepository aRepo;
    @Mock private VehicleRepository vRepo;
    @InjectMocks private AccessoryServiceImpl service;

    private UUID vehicleId;
    private Vehicle vehicle;
    private Accessory acc;

    @BeforeEach void init() {
        MockitoAnnotations.openMocks(this);
        vehicleId = UUID.randomUUID();
        vehicle = new Vehicle(); vehicle.setId(vehicleId);
        acc = new Accessory(); acc.setId(UUID.randomUUID()); acc.setVehicle(vehicle);
    }

    @Test void create_ShouldSave() {
        when(vRepo.findById(vehicleId)).thenReturn(Optional.of(vehicle));
        when(aRepo.save(any())).thenReturn(acc);
        Accessory result = service.create(vehicleId, new Accessory());
        assertEquals(vehicle, result.getVehicle());
    }

    @Test void create_ShouldThrow_WhenVehicleMissing() {
        when(vRepo.findById(vehicleId)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> service.create(vehicleId, new Accessory()));
    }

    @Test void listByVehicle_ShouldReturnList() {
        when(vRepo.existsById(vehicleId)).thenReturn(true);
        when(aRepo.findByVehicleId(vehicleId)).thenReturn(List.of(acc));
        List<Accessory> list = service.listByVehicle(vehicleId);
        assertEquals(1, list.size());
    }
}