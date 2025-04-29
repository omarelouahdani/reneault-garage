package org.sid.reneaultgarage.repository;

import org.sid.reneaultgarage.model.Garage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface GarageRepository extends JpaRepository<Garage, UUID>, JpaSpecificationExecutor<Garage> {}