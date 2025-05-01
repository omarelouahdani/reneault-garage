package org.sid.gestionGarage.dto;

import lombok.*;

import java.util.UUID;

/**
 * DTO exposé par l'API pour la ressource Accessory
 */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class AccessoryDto {
    private UUID id;
    private UUID vehicleId;
    private String name;
    private String description;
    private double price;
    private String type;
}