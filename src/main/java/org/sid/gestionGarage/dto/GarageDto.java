package org.sid.gestionGarage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GarageDto {
    private UUID id;
    private String name;
    private String address;
    private String telephone;
    private String email;
    /**
     * Clé = jour de la semaine, valeur = liste de paires start/end
     */
    private Map<String, List<OpeningTimeDto>> openingTimes;
}