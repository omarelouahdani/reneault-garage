package org.sid.reneaultgarage.controller;

import org.sid.reneaultgarage.dto.AccessoryDto;
import org.sid.reneaultgarage.mapper.AccessoryMapper;
import org.sid.reneaultgarage.model.Accessory;
import org.sid.reneaultgarage.service.AccessoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class AccessoryController {

    private final AccessoryService service;
    private final AccessoryMapper mapper;

    public AccessoryController(AccessoryService service, AccessoryMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    // POST /api/vehicles/{vehicleId}/accessories
    @PostMapping("/vehicles/{vehicleId}/accessories")
    public ResponseEntity<AccessoryDto> add(
            @PathVariable UUID vehicleId,
            @Valid @RequestBody AccessoryDto dto) {
        Accessory entity = mapper.toEntity(dto);
        Accessory saved = service.create(vehicleId, entity);
        return ResponseEntity.status(201).body(mapper.toDto(saved));
    }

    // PUT /api/vehicles/{vehicleId}/accessories/{id}
    @PutMapping("/vehicles/{vehicleId}/accessories/{id}")
    public ResponseEntity<AccessoryDto> update(
            @PathVariable UUID vehicleId,
            @PathVariable UUID id,
            @Valid @RequestBody AccessoryDto dto) {
        Accessory entity = mapper.toEntity(dto);
        Accessory updated = service.update(vehicleId, id, entity);
        return ResponseEntity.ok(mapper.toDto(updated));
    }

    // DELETE /api/vehicles/{vehicleId}/accessories/{id}
    @DeleteMapping("/vehicles/{vehicleId}/accessories/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID vehicleId,
            @PathVariable UUID id) {
        service.delete(vehicleId, id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/vehicles/{vehicleId}/accessories
    @GetMapping("/vehicles/{vehicleId}/accessories")
    public List<AccessoryDto> list(
            @PathVariable UUID vehicleId) {
        return service.listByVehicle(vehicleId).stream()
                .map(mapper::toDto)
                .toList();
    }
}
