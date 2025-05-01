package org.sid.gestionGarage.mapper;

import org.sid.gestionGarage.dto.AccessoryDto;
import org.sid.gestionGarage.model.Accessory;

public interface AccessoryMapper {

    public AccessoryDto toDto(Accessory entity);

    public Accessory toEntity(AccessoryDto dto);

}
