package org.sid.gestionGarage.specification;

import org.sid.gestionGarage.model.Garage;
import org.sid.gestionGarage.model.Vehicle;
import org.sid.gestionGarage.model.Accessory;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.*;

public class GarageSpecification {

    /** Recherche garages ayant au moins un véhicule de ce fuelType */
    public static Specification<Garage> hasVehicleFuelType(String fuelType) {
        return (Root<Garage> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Join<Garage, Vehicle> veh = root.join("vehicles", JoinType.INNER);
            return cb.equal(veh.get("fuelType"), fuelType);
        };
    }

    /** Recherche garages ayant au moins un accessoire nommé dans un de leurs véhicules */
    public static Specification<Garage> hasAccessoryName(String accessoryName) {
        return (Root<Garage> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Join<Garage, Vehicle> veh = root.join("vehicles", JoinType.INNER);
            Join<Vehicle, Accessory> acc = veh.join("accessories", JoinType.INNER);
            return cb.equal(acc.get("name"), accessoryName);
        };
    }
}