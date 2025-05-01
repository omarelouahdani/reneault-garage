package org.sid.gestionGarage.service;

import org.sid.gestionGarage.model.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface VehicleService {
    /**
     * 1. Créer un nouveau véhicule dans un garage donné
     *    - Vérifie le quota métier (max 50 véhicules)
     *    - Publie un événement après sauvegarde
     */
    Vehicle create(UUID garageId, Vehicle v) throws Exception;

    /**
     * 2. Mettre à jour un véhicule existant d’un garage
     *    - Vérifie que le véhicule appartient bien au garage
     */
    Vehicle update(UUID garageId, UUID vehicleId, Vehicle v);

    /**
     * 3. Supprimer un véhicule d’un garage
     *    - Vérifie que le véhicule appartient bien au garage
     */
    void delete(UUID garageId, UUID vehicleId);

    /**
     * 4. Lister les véhicules d’un garage spécifique (pagination + tri)
     */
    Page<Vehicle> listByGarage(UUID garageId, Pageable pageable);

    /**
     * 5. Lister tous les véhicules d’un modèle (brand) sur tous les garages
     */
    Page<Vehicle> listByModel(String brand, Pageable pageable);
}