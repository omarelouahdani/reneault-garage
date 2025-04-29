package org.sid.reneaultgarage.controller;


import org.sid.reneaultgarage.dto.VehicleDto;
import org.sid.reneaultgarage.mapper.VehicleMapper;
import org.sid.reneaultgarage.model.Vehicle;
import org.sid.reneaultgarage.service.VehicleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class VehicleController {

    private final VehicleService vehicleService;
    private final VehicleMapper vehicleMapper;

    public VehicleController(VehicleService vehicleService,
                             VehicleMapper vehicleMapper) {
        this.vehicleService = vehicleService;
        this.vehicleMapper = vehicleMapper;
    }

    /**
     * 1. Ajouter un véhicule dans un garage
     * POST /api/garages/{garageId}/vehicles
     */
    @PostMapping("/garages/{garageId}/vehicles")
    public ResponseEntity<VehicleDto> addVehicle(
            @PathVariable UUID garageId,
            @RequestBody VehicleDto dto) throws Exception {
        Vehicle entity = vehicleMapper.toEntity(dto);
        Vehicle saved = vehicleService.create(garageId, entity);
        return ResponseEntity.status(201).body(vehicleMapper.toDto(saved));
    }

    /**
     * 2. Modifier un véhicule existant dans un garage
     * PUT /api/garages/{garageId}/vehicles/{vehicleId}
     */
    @PutMapping("/garages/{garageId}/vehicles/{vehicleId}")
    public ResponseEntity<VehicleDto> updateVehicle(
            @PathVariable UUID garageId,
            @PathVariable UUID vehicleId,
            @RequestBody VehicleDto dto) {
        Vehicle entity = vehicleMapper.toEntity(dto);
        Vehicle updated = vehicleService.update(garageId, vehicleId, entity);
        return ResponseEntity.ok(vehicleMapper.toDto(updated));
    }

    /**
     * 3. Supprimer un véhicule d’un garage
     * DELETE /api/garages/{garageId}/vehicles/{vehicleId}
     */
    @DeleteMapping("/garages/{garageId}/vehicles/{vehicleId}")
    public ResponseEntity<Void> deleteVehicle(
            @PathVariable UUID garageId,
            @PathVariable UUID vehicleId) {
        vehicleService.delete(garageId, vehicleId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 4. Lister les véhicules d’un garage spécifique
     * GET /api/garages/{garageId}/vehicles
     */
    @GetMapping("/garages/{garageId}/vehicles")
    public Page<VehicleDto> listByGarage(
            @PathVariable UUID garageId,
            @PageableDefault(size = 20, sort = "manufactureYear") Pageable pageable) {
        return vehicleService.listByGarage(garageId, pageable)
                .map(vehicleMapper::toDto);
    }

    /**
     * 5. Lister tous les véhicules d’un modèle donné dans tous les garages
     * GET /api/vehicles?brand={brand}
     */
    @GetMapping("/vehicles")
    public Page<VehicleDto> listByModel(
            @RequestParam String brand,
            @PageableDefault(size = 20, sort = "manufactureYear") Pageable pageable) {
        return vehicleService.listByModel(brand, pageable)
                .map(vehicleMapper::toDto);
    }
}
