package org.sid.reneaultgarage.dto;

import lombok.*;

import java.util.UUID;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder
public class VehicleDto {
    private UUID id;
    private UUID garageId;
    private String brand;
    private int manufactureYear;
    private String fuelType;
}
