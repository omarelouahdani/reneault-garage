package org.sid.reneaultgarage.mapper.impl;

import org.sid.reneaultgarage.dto.AccessoryDto;
import org.sid.reneaultgarage.mapper.AccessoryMapper;
import org.sid.reneaultgarage.model.Accessory;
import org.sid.reneaultgarage.model.Vehicle;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Mapping manuel Entity ↔ DTO pour Accessory
 */
@Component
public class AccessoryMapperImpl implements AccessoryMapper {

    @Override
    public AccessoryDto toDto(Accessory entity) {
        if (entity == null) return null;
        AccessoryDto dto = new AccessoryDto();
        dto.setId(entity.getId());
        dto.setVehicleId(entity.getVehicle().getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setType(entity.getType());
        return dto;
    }

    @Override
    public Accessory toEntity(AccessoryDto dto) {
        if (dto == null) return null;
        Accessory entity = new Accessory();
        entity.setId(dto.getId());
        Vehicle v = new Vehicle();
        v.setId(dto.getVehicleId());
        entity.setVehicle(v);
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setType(dto.getType());
        return entity;
    }
}