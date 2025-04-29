package org.sid.reneaultgarage.controller;

import jakarta.validation.Valid;
import org.sid.reneaultgarage.dto.GarageDto;
import org.sid.reneaultgarage.mapper.GarageMapper;
import org.sid.reneaultgarage.model.Garage;
import org.sid.reneaultgarage.service.GarageService;
import org.sid.reneaultgarage.specification.GarageSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/garages")
public class GarageController {

    private final GarageService service;
    private final GarageMapper mapper;

    public GarageController(GarageService service, GarageMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<GarageDto> create(@Valid @RequestBody GarageDto dto) {
        Garage entity = mapper.toEntity(dto);
        Garage saved = service.create(entity);
        return ResponseEntity.status(201).body(mapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GarageDto> update(
            @PathVariable UUID id,
            @Valid @RequestBody GarageDto dto) {
        Garage entity = mapper.toEntity(dto);
        Garage updated = service.update(id, entity);
        return ResponseEntity.ok(mapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GarageDto> get(@PathVariable UUID id) {
        Garage g = service.get(id);
        return ResponseEntity.ok(mapper.toDto(g));
    }

    @GetMapping
    public Page<GarageDto> list(
            @PageableDefault(size=10, sort="name") Pageable pageable) {
        return service.list(pageable).map(mapper::toDto);
    }

    /**
     * GET /api/garages/search?fuelType=Essence&accessory=GPS
     */
    @GetMapping("/search")
    public Page<GarageDto> search(
            @RequestParam(required = false) String fuelType,
            @RequestParam(required = false) String accessory,
            @PageableDefault(size = 10, sort = "name") Pageable pageable) {

        Specification<Garage> spec = Specification.where(null);

        if (fuelType != null) {
            spec = spec.and(GarageSpecification.hasVehicleFuelType(fuelType));
        }
        if (accessory != null) {
            spec = spec.and(GarageSpecification.hasAccessoryName(accessory));
        }

        return service.search(spec, pageable)
                .map(mapper::toDto);
    }
}