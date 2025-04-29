package org.sid.reneaultgarage.mapper;

import org.sid.reneaultgarage.dto.AccessoryDto;
import org.sid.reneaultgarage.model.Accessory;

public interface AccessoryMapper {

    public AccessoryDto toDto(Accessory entity);

    public Accessory toEntity(AccessoryDto dto);

}
