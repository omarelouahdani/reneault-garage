package org.sid.reneaultgarage.service.impl;

import org.sid.reneaultgarage.dto.GarageDto;
import org.sid.reneaultgarage.exception.NotFoundException;
import org.sid.reneaultgarage.mapper.GarageMapper;
import org.sid.reneaultgarage.model.Garage;
import org.sid.reneaultgarage.repository.GarageRepository;
import org.sid.reneaultgarage.service.GarageService;
import org.sid.reneaultgarage.specification.GarageSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class GarageServiceImpl implements GarageService {

    private final GarageRepository repo;

    private GarageMapper mapper;

    public GarageServiceImpl(GarageRepository repo) {
        this.repo = repo;
    }

    @Override
    public Garage create(Garage g) {
        return repo.save(g);
    }

    @Override
    public Garage update(UUID id, Garage g) {
        Garage existing = repo.findById(id).orElseThrow(() -> new NotFoundException("Garage non trouvé"));
        existing.setName(g.getName());
        existing.setAddress(g.getAddress());
        existing.setTelephone(g.getTelephone());
        existing.setEmail(g.getEmail());
        existing.setOpeningTimes(g.getOpeningTimes());
        return repo.save(existing);
    }

    @Override
    public void delete(UUID id) {
        if (!repo.existsById(id)) throw new NotFoundException("Garage non trouvé");
        repo.deleteById(id);
    }

    @Override
    @Transactional(readOnly=true)
    public Garage get(UUID id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Garage non trouvé"));
    }

    @Override
    @Transactional(readOnly=true)
    public Page<Garage> list(Pageable pageable) {
        return repo.findAll(pageable);
    }

    /** Recherche dynamiquement selon la spec passée */
    @Override
    @Transactional(readOnly = true)
    public Page<Garage> search(Specification<Garage> spec, Pageable pageable) {
        return repo.findAll(spec, pageable);
    }
}
