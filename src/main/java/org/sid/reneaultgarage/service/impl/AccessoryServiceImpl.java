package org.sid.reneaultgarage.service.impl;

import org.sid.reneaultgarage.exception.NotFoundException;
import org.sid.reneaultgarage.model.Accessory;
import org.sid.reneaultgarage.model.Vehicle;
import org.sid.reneaultgarage.repository.AccessoryRepository;
import org.sid.reneaultgarage.repository.VehicleRepository;
import org.sid.reneaultgarage.service.AccessoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class AccessoryServiceImpl implements AccessoryService {

    private final AccessoryRepository aRepo;
    private final VehicleRepository vRepo;

    public AccessoryServiceImpl(AccessoryRepository aRepo, VehicleRepository vRepo) {
        this.aRepo = aRepo;
        this.vRepo = vRepo;
    }

    @Override
    public Accessory create(UUID vehicleId, Accessory a) {
        Vehicle v = vRepo.findById(vehicleId)
                .orElseThrow(() -> new NotFoundException("Vehicle not found"));
        a.setVehicle(v);
        return aRepo.save(a);
    }

    @Override
    public Accessory update(UUID vehicleId, UUID accessoryId, Accessory a) {
        vRepo.findById(vehicleId)
                .orElseThrow(() -> new NotFoundException("Vehicle not found"));
        Accessory existing = aRepo.findById(accessoryId)
                .orElseThrow(() -> new NotFoundException("Accessory not found"));
        if (!existing.getVehicle().getId().equals(vehicleId)) {
            throw new IllegalArgumentException("Accessory does not belong to this vehicle");
        }
        existing.setName(a.getName());
        existing.setDescription(a.getDescription());
        existing.setPrice(a.getPrice());
        existing.setType(a.getType());
        return aRepo.save(existing);
    }

    @Override
    public void delete(UUID vehicleId, UUID accessoryId) {
        Accessory existing = aRepo.findById(accessoryId)
                .orElseThrow(() -> new NotFoundException("Accessory not found"));
        if (!existing.getVehicle().getId().equals(vehicleId)) {
            throw new IllegalArgumentException("Accessory does not belong to this vehicle");
        }
        aRepo.delete(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Accessory> listByVehicle(UUID vehicleId) {
        if (!vRepo.existsById(vehicleId)) {
            throw new NotFoundException("Vehicle not found");
        }
        return aRepo.findByVehicleId(vehicleId);
    }
}