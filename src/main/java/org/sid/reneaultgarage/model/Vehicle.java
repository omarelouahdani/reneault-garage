package org.sid.reneaultgarage.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder
@Entity
public class Vehicle {
    @Id @GeneratedValue
    private UUID id;

    // Ajoutez ce setter manuellement
    @Setter
    @Getter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "garage_id")
    private Garage garage;

    @Column(nullable = false)
    private String brand;

    //année de fabrication.
    @Column(name = "manufacture_year", nullable = false)
    private int manufactureYear;

    //type_carburant.
    @Column(name = "fuel_type", nullable = false)
    private String fuelType;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Accessory> accessories = new ArrayList<>();


}
