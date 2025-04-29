package org.sid.reneaultgarage.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.sid.reneaultgarage.dto.VehicleDto;
import org.sid.reneaultgarage.model.Vehicle;

public interface VehicleMapper {

    VehicleDto toDto(Vehicle entity);

    Vehicle toEntity(VehicleDto dto);
}