package org.sid.reneaultgarage.service;

import org.sid.reneaultgarage.model.Accessory;

import java.util.List;
import java.util.UUID;

public interface AccessoryService {

    Accessory create(UUID vehicleId, Accessory a);
    Accessory update(UUID vehicleId, UUID accessoryId, Accessory a);
    void delete(UUID vehicleId, UUID accessoryId);
    List<Accessory> listByVehicle(UUID vehicleId);
}
