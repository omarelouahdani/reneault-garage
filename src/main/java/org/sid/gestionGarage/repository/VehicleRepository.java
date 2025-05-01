package org.sid.gestionGarage.repository;

import org.sid.gestionGarage.model.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {

    /**
     * Tous les véhicules d’un brand donné, dans *tous* les garages
     */
    Page<Vehicle> findByBrand(String brand, Pageable pageable);

    /**
     * Variante : tous les véhicules d’un brand donné, mais limités à une liste de garages
     */
    Page<Vehicle> findByBrandAndGarageIdIn(String brand, Iterable<UUID> garageIds, Pageable pageable);

    /** Pagination + tri des véhicules par garage */
    Page<Vehicle> findByGarageId(UUID garageId, Pageable pageable);
}
