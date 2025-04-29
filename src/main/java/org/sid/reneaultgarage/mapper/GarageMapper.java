package org.sid.reneaultgarage.mapper;

import org.sid.reneaultgarage.dto.GarageDto;
import org.sid.reneaultgarage.model.Garage;

public interface GarageMapper {

    public GarageDto toDto(Garage entity);

    public Garage toEntity(GarageDto dto);
}
