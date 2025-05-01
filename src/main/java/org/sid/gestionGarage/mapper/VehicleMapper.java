package org.sid.gestionGarage.mapper;


import org.sid.gestionGarage.dto.VehicleDto;
import org.sid.gestionGarage.model.Vehicle;

public interface VehicleMapper {

    VehicleDto toDto(Vehicle entity);

    Vehicle toEntity(VehicleDto dto);
}