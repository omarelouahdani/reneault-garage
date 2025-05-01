package org.sid.gestionGarage.mapper;

import org.sid.gestionGarage.dto.GarageDto;
import org.sid.gestionGarage.model.Garage;

public interface GarageMapper {

    public GarageDto toDto(Garage entity);

    public Garage toEntity(GarageDto dto);
}
