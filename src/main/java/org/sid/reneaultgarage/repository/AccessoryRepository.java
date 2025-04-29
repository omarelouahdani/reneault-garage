package org.sid.reneaultgarage.repository;

import org.sid.reneaultgarage.model.Accessory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccessoryRepository extends JpaRepository<Accessory, UUID> {
    List<Accessory> findByVehicleId(UUID vehicleId);
}