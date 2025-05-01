package org.sid.gestionGarage.repository;

import org.sid.gestionGarage.model.Garage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface GarageRepository extends JpaRepository<Garage, UUID>, JpaSpecificationExecutor<Garage> {}