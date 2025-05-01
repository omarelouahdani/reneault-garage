package org.sid.gestionGarage.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Setter
@Data @NoArgsConstructor @AllArgsConstructor
@Builder
public class Accessory {
    @Id @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private String type;
}