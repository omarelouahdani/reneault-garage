package org.sid.gestionGarage.service;

import org.sid.gestionGarage.model.Garage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;

public interface GarageService {

    Garage create(Garage g);
    Garage update(UUID id, Garage g);
    void delete(UUID id);
    Garage get(UUID id);
    Page<Garage> list(Pageable pageable);

    Page<Garage> search(Specification<Garage> spec, Pageable pageable);}
