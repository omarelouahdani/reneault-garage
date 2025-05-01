package org.sid.gestionGarage.mapper.impl;

import org.sid.gestionGarage.dto.VehicleDto;
import org.sid.gestionGarage.mapper.VehicleMapper;
import org.sid.gestionGarage.model.Vehicle;
import org.sid.gestionGarage.model.Garage;
import org.springframework.stereotype.Component;

/**
 * Implémentation native du mapping entre Vehicle et VehicleDto
 */
@Component
public class VehicleMapperImpl implements VehicleMapper {

    @Override
    public VehicleDto toDto(Vehicle entity) {
        if (entity == null) return null;
        VehicleDto dto = new VehicleDto();
        dto.setId(entity.getId());
        dto.setBrand(entity.getBrand());
        dto.setManufactureYear(entity.getManufactureYear());
        dto.setFuelType(entity.getFuelType());
        if (entity.getGarage() != null) {
            dto.setGarageId(entity.getGarage().getId());
        }
        return dto;
    }

    @Override
    public Vehicle toEntity(VehicleDto dto) {
        if (dto == null) return null;
        Vehicle entity = new Vehicle();
        entity.setId(dto.getId());
        entity.setBrand(dto.getBrand());
        entity.setManufactureYear(dto.getManufactureYear());
        entity.setFuelType(dto.getFuelType());
        if (dto.getGarageId() != null) {
            Garage g = new Garage();
            g.setId(dto.getGarageId());
            entity.setGarage(g);
        }
        return entity;
    }
}

