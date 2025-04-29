package org.sid.reneaultgarage.service.impl;

import jakarta.transaction.Transactional;
import org.sid.reneaultgarage.exception.NotFoundException;
import org.sid.reneaultgarage.exception.QuotaExceededException;
import org.sid.reneaultgarage.model.Garage;
import org.sid.reneaultgarage.model.Vehicle;
import org.sid.reneaultgarage.repository.GarageRepository;
import org.sid.reneaultgarage.repository.VehicleRepository;
import org.sid.reneaultgarage.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class VehicleServiceImpl implements VehicleService {

    private final KafkaTemplate<String, Vehicle> kafkaTemplate;
    private final VehicleRepository vehicleRepository;
    private final GarageRepository gRepo;

    public VehicleServiceImpl(VehicleRepository repo, GarageRepository gRepo, KafkaTemplate<String, Vehicle> kafkaTemplate) {
        this.vehicleRepository = repo;
        this.gRepo = gRepo;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public Vehicle create(UUID garageId, Vehicle vehicle) throws Exception {
        Garage g = gRepo.findById(garageId)
                .orElseThrow(() -> new Exception("Garage non trouvé"));
        if (g.getVehicles().size() >= 50) {
            throw new QuotaExceededException("garage peut stocker au maximum 50 véhicules. vous pouvez pas ajouter autre vehicle");
        }
        vehicle.setGarage(g);

        Vehicle saved = vehicleRepository.save(vehicle);
        // Publier l’événement
        //kafkaTemplate.send("vehicles-topic", saved);
        return saved;
    }

    @Override
    public Vehicle update(UUID garageId, UUID vehicleId, Vehicle v) {
        gRepo.findById(garageId)
                .orElseThrow(() -> new NotFoundException("Garage non trouvé"));
        Vehicle existing = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new NotFoundException("Véhicule non trouvé"));
        if (!existing.getGarage().getId().equals(garageId)) {
            throw new IllegalArgumentException("Le véhicule n'appartient pas à ce garage");
        }
        // Mise à jour des attributs autorisés
        existing.setBrand(v.getBrand());
        existing.setManufactureYear(v.getManufactureYear());
        existing.setFuelType(v.getFuelType());
        return vehicleRepository.save(existing);
    }

    /**
     * Étape 3: Suppression d’un véhicule
     *  - Vérifier l’existence et l’appartenance
     *  - Supprimer
     */
    @Override
    public void delete(UUID garageId, UUID vehicleId) {
        Vehicle existing = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new NotFoundException("Véhicule non trouvé"));
        if (!existing.getGarage().getId().equals(garageId)) {
            throw new IllegalArgumentException("Le véhicule n'appartient pas à ce garage");
        }
        vehicleRepository.delete(existing);
    }

    /**
     * Étape 4: Liste par garage
     *  - Vérifier que le garage existe
     *  - Retourner page de véhicules
     */
    @Override
    public Page<Vehicle> listByGarage(UUID garageId, Pageable pageable) {
        if (!gRepo.existsById(garageId)) {
            throw new NotFoundException("Garage non trouvé");
        }
        return vehicleRepository.findByGarageId(garageId, pageable);
    }

    /**
     * Étape 5: Liste par modèle (brand) sur tous les garages
     */
    @Override
    public Page<Vehicle> listByModel(String brand, Pageable pageable) {
        return vehicleRepository.findByBrand(brand, pageable);
    }
}
